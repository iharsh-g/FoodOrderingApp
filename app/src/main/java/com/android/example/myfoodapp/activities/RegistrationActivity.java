package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.myfoodapp.MainActivity;
import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.LoginData;
import com.android.example.myfoodapp.database.LoginRoomDb;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPass;
    private LoginRoomDb mLoginRoomDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mLoginRoomDb = LoginRoomDb.getDatabase(this);

        mName = findViewById(R.id.et_regis_name);
        mEmail = findViewById(R.id.et_regis_email);
        mPass = findViewById(R.id.et_regis_password);

        Button bt = findViewById(R.id.bt_register);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString();
                String Email = mEmail.getText().toString();
                String Pass = mPass.getText().toString();

                if (Name.equals("") || Email.equals("") || Pass.equals("")) {
                    makeErrorDialog();
                    return;
                }

                makeDetailsDialog(Name, Email, Pass);
            }
        });

        TextView tv = findViewById(R.id.tv_sign_in);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void makeErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Please fill all the Details!");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void makeDetailsDialog(String name, String email, String pass) {
        if(mLoginRoomDb.loginDao().isEmailExist(email)) {
            makeEmailExistDialog();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Given Details - ");
        dialog.setMessage(name);
        dialog.setMessage(email);
        dialog.setMessage("Name - " + name + "\nEmail - " + email + "\nPassword - " + pass);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginData data = new LoginData();
                data.setName(name);
                data.setEmail(email);
                data.setPassword(pass);
                mLoginRoomDb.loginDao().insert(data);

                SharedPreferences sharedPreferences = getSharedPreferences("SignInPreference", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("signedIn", true);
                editor.apply();

                SharedPreferences sharedPreferencesEmail = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
                editor = sharedPreferencesEmail.edit();
                editor.putString("email", email);
                editor.apply();

                SharedPreferences sharedPreferencesCnt = getSharedPreferences("orderHistoryCount", Context.MODE_PRIVATE);
                editor = sharedPreferencesCnt.edit();
                editor.putInt("cnt", -1);
                editor.apply();

                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                dialog.cancel();
            }
        });

        dialog.setNegativeButton("Wait", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void makeEmailExistDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Email Already Exist, Try Different One!");
        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}