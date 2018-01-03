package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.ErrorList;
import com.monapizza.monapizza.core.User;

public class AccountManagerActivity extends AppCompatActivity {
    Button mAccounManagerEditButton;
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

        mAccounManagerEditButton = (Button) findViewById(R.id.account_manager_edit_button);

        final ConstraintLayout AccountManagerEditFrame = (ConstraintLayout) findViewById(R.id.account_manager_edit);

        final TableLayout AccountMangerInfo = (TableLayout)findViewById(R.id.account_manager_info);

        mAccounManagerEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountMangerInfo.setVisibility(View.GONE);
                mAccounManagerEditButton.setVisibility(View.GONE);
                AccountManagerEditFrame.setVisibility(View.VISIBLE);
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
                mAccounManagerEditButton.setVisibility(View.VISIBLE);
            }
        });
    }
}
