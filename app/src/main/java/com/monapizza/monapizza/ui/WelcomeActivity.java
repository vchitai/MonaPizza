package com.monapizza.monapizza.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.User;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (User.getInstance().skipSignIn() == 1) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        setContentView(R.layout.activity_welcome);
        final Context context          = this;

        Button        guestLoginButton = (Button)findViewById(R.id.welcome_guest_login_button);
        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GuestLoginActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = (Button)findViewById(R.id.welcome_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
            }
        });

        Button signupButton = (Button)findViewById(R.id.welcome_signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
