package net.juanfrancisco.blog.devhacktodo.presenter.presenters;

import net.juanfrancisco.blog.devhacktodo.domain.models.User;
import net.juanfrancisco.blog.devhacktodo.domain.usecases.UserUseCase;
import net.juanfrancisco.blog.devhacktodo.domain.usecases.impl.UserUseCaseImpl;
import net.juanfrancisco.blog.devhacktodo.helpers.CallBack;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces.SignUpContract;

/**
 * Created by franc on 3/12/2017.
 */

public class SignUpPresenter implements SignUpContract.UserActionsListener {

    private SignUpContract.SignUpContractView view;
    private UserUseCase userUseCase;

    public SignUpPresenter(SignUpContract.SignUpContractView view) {
        this.view = view;
        this.userUseCase = new UserUseCaseImpl();
    }

    @Override
    public void onSignUp(String fullName, String email, String password) {
        userUseCase.signUp(fullName, email, password, new CallBack<User>() {
            @Override
            public void success(User result) {
                view.goToMainActivity();
            }

            @Override
            public void error(Exception error) {
                view.showMessageError(error);
            }
        });
    }
}
