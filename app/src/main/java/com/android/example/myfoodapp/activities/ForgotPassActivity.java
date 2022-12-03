package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.MotionEvent;
import android.view.View;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.LoginRoomDb;
import com.android.example.myfoodapp.databinding.ActivityForgotPassBinding;

@SuppressLint("ClickableViewAccessibility")
public class ForgotPassActivity extends AppCompatActivity {

    private ActivityForgotPassBinding mBinding;
    private boolean isVisible1, isVisible2;
    private LoginRoomDb mLoginRoomDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pass);

        mLoginRoomDb = LoginRoomDb.getDatabase(this);

        mBinding.et1stPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= mBinding.et1stPassword.getRight() - mBinding.et1stPassword.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = mBinding.et1stPassword.getSelectionEnd();
                        if(isVisible1) {
                            //drawable image
                            mBinding.et1stPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_of, 0);
                            //For hide Password
                            mBinding.et1stPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isVisible1 = false;
                        }
                        else {
                            //drawable image
                            mBinding.et1stPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                            //For show Password
                            mBinding.et1stPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isVisible1 = true;
                        }
                        mBinding.et1stPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });


        mBinding.et2ndPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= mBinding.et2ndPassword.getRight() - mBinding.et2ndPassword.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = mBinding.et2ndPassword.getSelectionEnd();
                        if(isVisible2) {
                            //drawable image
                            mBinding.et2ndPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_of, 0);
                            //For hide Password
                            mBinding.et2ndPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isVisible2 = false;
                        }
                        else {
                            //drawable image
                            mBinding.et2ndPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                            //For show Password
                            mBinding.et2ndPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isVisible2 = true;
                        }
                        mBinding.et2ndPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        mBinding.btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBinding.et1stPassword.getText().toString().equals(mBinding.et2ndPassword.getText().toString())) {
                    String email = mBinding.etEmail.getText().toString();

                    if(!mLoginRoomDb.loginDao().isEmailExist(email)) {
                        makeEmailErrorDialog();
                        return;
                    }

                    mLoginRoomDb.loginDao().setPassword(email, mBinding.et1stPassword.getText().toString());
                    makeDialogOk();
                    finish();
                }
                else {
                    makeDialogErrorPass();
                }
            }
        });
    }

    private void makeEmailErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Email does not Exist!");
        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void makeDialogOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Password has been successfully changed");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void makeDialogErrorPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Passwords are not matching");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}