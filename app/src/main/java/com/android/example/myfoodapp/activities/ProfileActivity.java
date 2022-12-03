package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.LoginRoomDb;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView mName, mEmail;
        mName = findViewById(R.id.tv_user);
        mEmail = findViewById(R.id.tv_email);

        EditText mAddress, mPhoneNo;
        mAddress = findViewById(R.id.et_address);
        mPhoneNo = findViewById(R.id.et_phoneNo);

        SharedPreferences sharedPreferencesEmail = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");

        LoginRoomDb loginRoomDb = LoginRoomDb.getDatabase(this);
        mName.setText(loginRoomDb.loginDao().getUserName(email));
        mEmail.setText(loginRoomDb.loginDao().getUserEmail(email));

        if(loginRoomDb.loginDao().getUserAddress(email) != null) {
            mAddress.setText(loginRoomDb.loginDao().getUserAddress(email));
        }

        if(loginRoomDb.loginDao().getUserPhoneNo(email) != null) {
            mPhoneNo.setText(loginRoomDb.loginDao().getUserPhoneNo(email));
        }

        Button bt = findViewById(R.id.bt_done);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add, num;
                add = mAddress.getText().toString();
                num = mPhoneNo.getText().toString();

                loginRoomDb.loginDao().setUserAddress(add, email);
                loginRoomDb.loginDao().setUserPhoneNo(num, email);

                finish();
            }
        });
    }
}