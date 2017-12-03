package net.juanfrancisco.blog.devhacktodo.presenter.views.fragments.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import net.juanfrancisco.blog.devhacktodo.R;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.AuthPresenter;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces.AuthContract;
import net.juanfrancisco.blog.devhacktodo.presenter.views.activities.auth.AuthActivity;
import net.juanfrancisco.blog.devhacktodo.presenter.views.master.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by franc on 2/12/2017.
 */

public class LoginFragment extends Fragment implements AuthContract.AuthContractView,Validator.ValidationListener {

    private AuthContract.UserActionsLister mActionsListener;

    @NotEmpty(message = "Campo Obligatorio.")
    @Email(message = "Debe ingresar un correo valido.")
    @BindView(R.id.edEmail)
    EditText edEmail;

    @NotEmpty(message = "Campo Obligatorio.")
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC,message = "Se requieren 6 carateres alfa numericos")
    @BindView(R.id.edPassword)
    EditText edPassword;


    @BindView(R.id.ckbRemeber)
    CheckBox ckbRemeber;


    @BindView(R.id.btnEntrar)
    Button btnEntrar;
    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;
    Unbinder unbinder;

    Validator validator;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }


    @Override
    public void showMessageError(Exception error) {
        Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        validator = new Validator(this);
        validator.setValidationListener(this);

        mActionsListener = new AuthPresenter(this);
//        Toast.makeText(this.getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnEntrar, R.id.btnRegistrar})
    public void onViewClicked(View view)
    {


        switch (view.getId())
        {
            case R.id.btnEntrar:
                validator.validate();
                this.goToMainActivity();
//                Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();

            case R.id.btnRegistrar:
                this.goToSignUpFragment();
                break;

        }
    }

    private void goToSignUpFragment()
    {

        AuthActivity authActivity = (AuthActivity) getActivity();
        authActivity.replaceFragment(SignUpFragment.getInstance(), true);

    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this.getActivity(), "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onLogin() {
        try {
            boolean result = true;
            String email = this.edEmail.getText().toString();
            String password = this.edPassword.getText().toString();
            boolean remember = this.ckbRemeber.isChecked();




            mActionsListener.onLogin(email, password, remember);

        } catch (Exception e) {

        }
    }


    @Override
    public void goToLoginFragment() {

    }

    @Override
    public void goToSinUpFragment() {

    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
