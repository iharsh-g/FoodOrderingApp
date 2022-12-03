package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.myfoodapp.MainActivity;
import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.LoginRoomDb;

public class LoginActivity extends AppCompatActivity {

    private LoginRoomDb mLoginRoomDb;
    private EditText mEmail, mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginRoomDb = LoginRoomDb.getDatabase(this);

        mEmail = findViewById(R.id.et_signIn_email);
        mPass = findViewById(R.id.et_signIn_password);

        TextView tv = findViewById(R.id.tv_register);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        Button bt = findViewById(R.id.bt_signIn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SignIn", "Clicking");
                String Email = mEmail.getText().toString();
                String Pass = mPass.getText().toString();

                if(mLoginRoomDb.loginDao().isRowExist(Email, Pass)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("SignInPreference", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("signedIn", true);
                    editor.apply();

                    SharedPreferences sharedPreferencesEmail = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
                    editor = sharedPreferencesEmail.edit();
                    editor.putString("email", Email);
                    editor.apply();

                    int val = mLoginRoomDb.loginDao().getUserCount(Email);
                    SharedPreferences sharedPreferencesCnt = getSharedPreferences("orderHistoryCount", Context.MODE_PRIVATE);
                    editor = sharedPreferencesCnt.edit();
                    editor.putInt("cnt", val);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    makeDialog();
                }
            }
        });

        TextView textViewForgotPass = findViewById(R.id.tv_forgot_pass);
        textViewForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });
    }

    private void makeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention!");
        builder.setMessage("Either Email or Password is invalid");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}