package net.juanfrancisco.blog.devhacktodo.presenter.views.activities.auth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.juanfrancisco.blog.devhacktodo.R;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.AuthPresenter;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces.AuthContract;
import net.juanfrancisco.blog.devhacktodo.presenter.views.fragments.auth.LoginFragment;

/**
 * Created by franc on 2/12/2017.
 */

public class AuthActivity extends AppCompatActivity implements AuthContract.AuthContractView {

    private AuthContract.UserActionsLister mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);



        mActionsListener = new AuthPresenter(this);
        mActionsListener.goToLoginFragment();

//        mActionsListener.goToLoginFragment();

        //mActionsListener.goToLoginFragment();



    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        if(addToBackStack)
        {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void goToLoginFragment() {

        replaceFragment(LoginFragment.getInstance(), true);
    }

    @Override
    public void goToSinUpFragment()
    {

    }

    @Override
    public void goToMainActivity()
    {

    }


    @Override
    public void showMessageError(Exception error)
    {
        //Snackbar.make(this, error.getMessage(), Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
