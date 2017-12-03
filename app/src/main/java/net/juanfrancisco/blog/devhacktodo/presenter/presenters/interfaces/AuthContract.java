package net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces;

/**
 * Created by franc on 2/12/2017.
 */

public interface AuthContract {

    //Interface que implementara el Fragment o Activity
    interface AuthContractView
    {
        void goToLoginFragment();
        void goToSinUpFragment();
        void goToMainActivity();
        void showMessageError(Exception error);
    }

    //Interface que implementara el presenter
    interface UserActionsLister
    {
        void goToLoginFragment();

        void onLogin(String email, String password, boolean remember);
    }

}
