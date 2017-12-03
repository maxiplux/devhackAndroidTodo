package net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces;

/**
 * Created by franc on 3/12/2017.
 */


public interface SignUpContract {

    interface SignUpContractView {
        void goToLoginFragment();

        void goToMainActivity();

        void showMessageError(Exception error);
    }

    interface UserActionsListener {
        void onSignUp(String fullName, String email, String password);
    }

}