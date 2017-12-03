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
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.Validator;

import net.juanfrancisco.blog.devhacktodo.R;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.SignUpPresenter;
import net.juanfrancisco.blog.devhacktodo.presenter.presenters.interfaces.SignUpContract;
import net.juanfrancisco.blog.devhacktodo.presenter.views.master.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment  implements SignUpContract.SignUpContractView, ValidationListener{

    @NotEmpty(message = "Campo Obligatorio.")
    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @NotEmpty(message = "Campo Obligatorio.")
    @Email(message = "Debe ingresar un correo valido.")
    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @NotEmpty(message = "Campo Obligatorio.")
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC,message = "Se requieren 6 carateres alfa numericos")
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;

    @BindView(R.id.btGoLogin)
    Button btGoLogin;


    @BindView(R.id.chkRemeber)
    CheckBox chkRemeber;


    Unbinder unbinder;
    Validator validator ;


    private SignUpContract.UserActionsListener mActionsListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment getInstance() {
        return new SignUpFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        unbinder = ButterKnife.bind(this, view);

        validator = new Validator(this);
        validator.setValidationListener(this);

        mActionsListener = new SignUpPresenter(this);

        return view;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnRegistrar, R.id.btGoLogin})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.btnRegistrar:
                validator.validate();
                this.signUp();

            case R.id.btGoLogin:
                break;
        }
    }

    public void signUp() {
        try {



            //Si la validaciones no generaron errores
            String fullName =this.edtFullName.getText().toString();
            String email =this.edtEmail.getText().toString();
            String password =this.edtPassword.getText().toString();

            mActionsListener.onSignUp(fullName, email, password);

        } catch (Exception e) {
            this.showMessageError(e);

        }
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


    @Override
    public void goToLoginFragment() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessageError(Exception error) {
        Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_LONG).show();
    }


}
