package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.ErrorList;
import com.monapizza.monapizza.core.User;

public class AccountManagerActivity extends AppCompatActivity {
    Button mAccountManagerEditButton;
    Button mAccountManagerLogoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.account_manager_toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        TextView AccountManagerUsername = (TextView) findViewById(R.id.account_manager_username);
        TextView AccountManagerEmail = (TextView) findViewById(R.id.account_manager_email);

        AccountManagerUsername.setText(User.getInstance().getUserName());
        String defaultEmail = User.getInstance().getUserName() + getResources().getString(R.string.default_email);
        AccountManagerEmail.setText(defaultEmail);
        mAccountManagerEditButton = (Button) findViewById(R.id.account_manager_edit_button);
        mAccountManagerLogoutButton = (Button) findViewById(R.id.account_manager_logout_button);

        mAccountManagerLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        final ConstraintLayout AccountManagerEditFrame = (ConstraintLayout) findViewById(R.id.account_manager_edit);

        final TableLayout AccountMangerInfo = (TableLayout)findViewById(R.id.account_manager_info);

        mAccountManagerEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountMangerInfo.setVisibility(View.GONE);
                mAccountManagerEditButton.setVisibility(View.GONE);
                AccountManagerEditFrame.setVisibility(View.VISIBLE);
                mAccountManagerLogoutButton.setVisibility(View.GONE);
            }
        });

        final TextInputEditText passwordIET              = (TextInputEditText) findViewById(R.id.account_manager_password_textInputEditText);
        final TextInputEditText repasswordIET            = (TextInputEditText) findViewById(R.id.account_manager_repassword_textInputEditText);
        Button                  AccountManagerEditButton = (Button) findViewById(R.id.account_manager_button);
        AccountManagerEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordIET.getText().toString().equals(repasswordIET.getText().toString())) {
                    if (User.getInstance().updatePass(User.getInstance().getUserName(),passwordIET.getText().toString()) == 1) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.IS_UpdatePassSuccess), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), ErrorList.getMessage(ErrorList.getExitCode()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.EM_PasswordConfirmNotMatch), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button AccountManagerFinishButton = (Button) findViewById(R.id.account_manager_finish_edit_button);
        AccountManagerFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManagerEditFrame.setVisibility(View.GONE);
                AccountMangerInfo.setVisibility(View.VISIBLE);
                mAccountManagerEditButton.setVisibility(View.VISIBLE);
                mAccountManagerLogoutButton.setVisibility(View.VISIBLE);
            }
        });
    }
}
