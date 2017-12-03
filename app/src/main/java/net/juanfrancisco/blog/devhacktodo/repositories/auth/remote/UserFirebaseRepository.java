package net.juanfrancisco.blog.devhacktodo.repositories.auth.remote;

import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.juanfrancisco.blog.devhacktodo.domain.models.User;
import net.juanfrancisco.blog.devhacktodo.helpers.CallBack;
import net.juanfrancisco.blog.devhacktodo.repositories.auth.UserRepository;

/**
 * Created by franc on 2/12/2017.
 */

public class UserFirebaseRepository implements UserRepository {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public UserFirebaseRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance()
                .getReference("users");
    }

    @Override
    public void login(String email, String password, final CallBack<User> CallBack) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful() && task.getResult() != null) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            mDatabase.child(firebaseUser.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User user = dataSnapshot.getValue(User.class);
                                            CallBack.success(user);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            CallBack.error(databaseError.toException());
                                        }
                                    });
                        } else {
                            CallBack.error(task.getException());
                        }
                    }
                });
    }

    @Override
    public void signUp(final User user, final CallBack<User> CallBack) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Respuesta de la creacion del usuario en FirebaseAuthentication
                        if(task.isSuccessful() && task.getResult() != null) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            user.setId(firebaseUser.getUid());
                            user.setPassword(null);
                            mDatabase.child(user.getId()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //Respuesta de la creacion del usuario en FirebaseDatabase
                                            if(task.isSuccessful()) {
                                                CallBack.success(user);
                                            } else {
                                                CallBack.error(task.getException());
                                            }
                                        }
                                    });
                        } else {
                            CallBack.error(task.getException());
                        }
                    }
                });
    }
}

