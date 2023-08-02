package com.tanya.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREF_NAME = "my_shared_pref";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    public static final String KEY_USER_ID = "user_id";
    CardView getStartedCv;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStartedCv = findViewById(R.id.cardView);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getStartedCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    Intent openSignIn = new Intent(MainActivity.this, UserListActivity.class);
                    startActivity(openSignIn);
                } else {
                    Intent openSignIn = new Intent(MainActivity.this, SignIn.class);
                    startActivity(openSignIn);
                }

            }
        });
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

}