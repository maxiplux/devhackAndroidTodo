package net.juanfrancisco.blog.devhacktodo.domain.usecases.impl;

import net.juanfrancisco.blog.devhacktodo.domain.models.User;
import net.juanfrancisco.blog.devhacktodo.domain.usecases.UserUseCase;
import net.juanfrancisco.blog.devhacktodo.helpers.CallBack;
import net.juanfrancisco.blog.devhacktodo.repositories.auth.UserRepository;
import net.juanfrancisco.blog.devhacktodo.repositories.auth.remote.UserFirebaseRepository;

/**
 * Created by franc on 3/12/2017.
 */

public class UserUseCaseImpl implements UserUseCase {

    private UserRepository userRepository;

    public UserUseCaseImpl() {
        this.userRepository = new UserFirebaseRepository();
    }

    @Override
    public void login(String email, String password, final boolean remember, final CallBack<User> callback) {
        userRepository.login(email, password, new CallBack<User>() {
            @Override
            public void success(User user) {
                if (user != null && remember) {
                    //TODO GUARDAR EMAIL EN SharedPreferences
                }
                callback.success(user);
            }

            @Override
            public void error(Exception error) {
                callback.error(error);
            }
        });
    }


    @Override
    public void signUp(String fullName, String email, String password, final CallBack<User> callback) {
        User user = new User(fullName, email, password);
        userRepository.signUp(user, new CallBack<User>() {
            @Override
            public void success(User user) {
                callback.success(user);
            }

            @Override
            public void error(Exception error) {
                callback.error(error);
            }
        });
    }


}
