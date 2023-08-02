package com.tanya.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

public class AddUserActivity extends AppCompatActivity {
    CardView addUserCv, cancelCv;
    Context context;
    MaterialToolbar materialToolbar;
    TextView pageTitle;
    ImageView backImg, addImg;
    TextInputLayout emailLayout, firstNameLayout, lastNameLayout;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        init();
        pageTitle.setText("Add User");
        cancelCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetViews();
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UserListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        addUserCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validateLastName() | !validateFirstName()) {
                    return;
                } else {
                    float result = databaseHelper.addUserForWeather(emailLayout.getEditText().getText().toString().trim(), firstNameLayout.getEditText().getText().toString().trim(), lastNameLayout.getEditText().getText().toString().trim());
                    if (result == -1) {
                        Toast.makeText(context, "Failed to Add User", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "User Added Successfully", Toast.LENGTH_SHORT).show();
                        resetViews();
                    }
                }
            }

        });
    }

    private void resetViews() {
        firstNameLayout.getEditText().setText(null);
        emailLayout.getEditText().setText(null);
        lastNameLayout.getEditText().setText(null);
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

    private boolean isValidEmail(String email) {
        // Use a regular expression to check if the email follows a valid format
        // This regex pattern is a simple version for demonstration purposes,
        // you can use a more complex pattern for production use.
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // You can also use Android's built-in Patterns.EMAIL_ADDRESS for basic validation
        // boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        return email.matches(emailPattern);
    }

    private boolean validateFirstName() {
        String firstNameInput = firstNameLayout.getEditText().getText().toString().trim();
        if (firstNameInput.isEmpty()) {
            firstNameLayout.setError(getResources().getString(R.string.fieldEmptyError));
            return false;
        } else {
            firstNameLayout.setError(null);
            return true;
        }
    }

    private boolean validateLastName() {
        String lastNameInput = lastNameLayout.getEditText().getText().toString().trim();
        if (lastNameInput.isEmpty()) {
            lastNameLayout.setError(getResources().getString(R.string.fieldEmptyError));
            return false;
        } else {
            lastNameLayout.setError(null);
            return true;
        }
    }

    public void init() {
        context = this;
        databaseHelper = new DatabaseHelper(context);
        materialToolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        pageTitle = (TextView) findViewById(R.id.toolbarTitle);
        backImg = (ImageView) findViewById(R.id.back_toolbar);
        addImg = (ImageView) findViewById(R.id.more_toolbar);
        firstNameLayout = (TextInputLayout) findViewById(R.id.fnameTxtLayout);
        lastNameLayout = (TextInputLayout) findViewById(R.id.lastNametextInputLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.emailTxtInputLayout);
        cancelCv = (CardView) findViewById(R.id.cancelCardView);
        addUserCv = (CardView) findViewById(R.id.addUsercardView);
        addImg.setVisibility(View.GONE);
    }

}