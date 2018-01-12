package com.novuslogics.roomexample.pages;

import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.novuslogics.roomexample.R;
import com.novuslogics.roomexample.RoomExampleApplication;
import com.novuslogics.roomexample.dao.UserDao;
import com.novuslogics.roomexample.model.User;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Inject
    UserDao userDao;

    @OnClick(R.id.btn_saveData)
    public void validateAndSave() {
        validator.validate();
    }

    @NotEmpty(message = "Name is required", sequence = 1)
    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @NotEmpty(message = "Email is required", sequence = 1)
    @Email(message = "Email is invalid", sequence = 2)
    @BindView(R.id.et_email_address)
    EditText et_email_address;

    @NotEmpty(sequence = 1, message = "Mobile number is required")
    @Length(min = 10, max = 10, message = "Please enter a valid mobile number", sequence = 2)
    @BindView(R.id.et_mobile_number)
    EditText et_mobile_number;

    @BindView(R.id.tv_results)
    TextView tv_results;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomExampleApplication.getComponent(this).inject(this);
        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                String userName = et_user_name.getText().toString();
                String userEmail = et_email_address.getText().toString();
                String userPhone = et_mobile_number.getText().toString();

                new DatabaseAsync().execute(new User(userName, userEmail, userPhone));
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(MainActivity.this);

                    // Display error messages ;)
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        TypefaceProvider.getRegisteredIconSets();

        userDao.fetchAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> userList) {
                tv_results.setText(userList.toString());
            }
        });
    }

    private class DatabaseAsync extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            User user = users[0];
            userDao.insertOnlySingleRecord(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            et_user_name.setText("");
            et_email_address.setText("");
            et_mobile_number.setText("");

            Toast.makeText(MainActivity.this, "Saved Data successfully", Toast.LENGTH_SHORT).show();

        }
    }
}

