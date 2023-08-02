package com.tanya.weather;

import static com.tanya.weather.MainActivity.KEY_USER_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity {
    CardView loginCv;
    Context context;
    TextInputEditText emailEt, passwordEt;
    TextInputLayout emailLayout, passwordLayout;
    DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        loginCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()) {
                    return;
                } else {
                    addUserAdminToDatabase(emailLayout.getEditText().getText().toString().trim(), passwordLayout.getEditText().getText().toString().trim());
                }

            }
        });
    }

    private void addUserAdminToDatabase(String email, String password) {
        float resultMessage = databaseHelper.addNewAdminUser(email, password);
        if (resultMessage == -1) {
            Toast.makeText(context, "Failed to Login", Toast.LENGTH_SHORT).show();
        } else {
            // Save the user ID in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USER_ID, email);
            editor.putBoolean(MainActivity.KEY_IS_LOGGED_IN, true);
            editor.apply();
            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
            //Move to UserListActivity
            Intent openUserList = new Intent(SignIn.this, UserListActivity.class);
            startActivity(openUserList);
            finish();
        }

    }


    private boolean validateEmail() {
        String emailInput = emailLayout.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailLayout.setError(getResources().getString(R.string.fieldEmptyError));
            return false;
        } else if (!isValidEmail(emailInput)) {
            emailLayout.setError(getResources().getString(R.string.invalidEmailError));
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = passwordLayout.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            passwordLayout.setError(getResources().getString(R.string.fieldEmptyError));
            return false;
        } else if (passwordInput.length() < 6) {
            passwordLayout.setError(getResources().getString(R.string.passwordTooShortError));
            return false;
        } else {
            passwordLayout.setError(null);
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        // Use a regular expression to check if the email follows a valid format
        // This regex pattern is a simple version for demonstration purposes,
        // you can use a more complex pattern for production use.
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // You can also use Android's built-in Patterns.EMAIL_ADDRESS for basic validation
        // boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        return email.matches(emailPattern);
    }

    public void init() {
        context = this;
        emailEt = (TextInputEditText) findViewById(R.id.email_et);
        passwordEt = (TextInputEditText) findViewById(R.id.password);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordTxtInputLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.email_textInputLayout);
        loginCv = (CardView) findViewById(R.id.login_cardView);
        databaseHelper = new DatabaseHelper(context);
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
    }
}