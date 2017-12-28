package com.monapizza.monapizza.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.ErrorList;
import com.monapizza.monapizza.core.User;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final TextInputEditText usernameIET = (TextInputEditText) findViewById(R.id.signup_username_textInputEditText);
        final TextInputEditText passwordIET = (TextInputEditText) findViewById(R.id.signup_password_textInputEditText);
        final TextInputEditText repasswordIET = (TextInputEditText) findViewById(R.id.signup_repassword_textInputEditText);

        final Context           context     = this;
        Button                  loginButton = (Button)findViewById(R.id.signup_signup_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordIET.getText().toString().equals(repasswordIET.getText().toString())) {
                    if (User.getInstance().signUp(usernameIET.getText().toString(), passwordIET.getText().toString()) == 1) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, ErrorList.getMessage(ErrorList.getExitCode()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, getResources().getString(R.string.EM_PasswordConfirmNotMatch), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
