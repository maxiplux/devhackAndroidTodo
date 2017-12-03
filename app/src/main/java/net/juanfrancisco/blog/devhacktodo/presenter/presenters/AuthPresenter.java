package net.juanfrancisco.blog.devhacktodo.presenter.presenters;

import net.juanfrancisco.blog.devhacktodo.domain.models.User;
import net.juanfrancisco.blog.devhacktodo.domain.usecases.UserUseCase;
import net.juanfrancisco.blog.devhacktodo.helpers.CallBack;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces.AuthContract;

/**
 * Created by franc on 2/12/2017.
 */

public class AuthPresenter implements AuthContract.UserActionsLister {

    private AuthContract.AuthContractView view;
    private UserUseCase userUseCase;

    public AuthPresenter(AuthContract.AuthContractView view) {
        this.view = view;
    }

    @Override
    public void goToLoginFragment()
    {
        this.view.goToLoginFragment();
    }

    @Override
    public void onLogin(String email, String password, boolean remember) {
        userUseCase.login(email, password, remember, new CallBack<User>() {
            @Override
            public void success(User user) {
                view.goToMainActivity();
            }

            @Override
            public void error(Exception error) {
                view.showMessageError(error);
            }
        });
    }
}
