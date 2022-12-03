package com.android.example.myfoodapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.example.myfoodapp.activities.ProfileActivity;
import com.android.example.myfoodapp.activities.WelcomeActivity;
import com.android.example.myfoodapp.database.LoginRoomDb;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.android.example.myfoodapp.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

//        if(!NetworkUtils.isNetworkAvailable(this)){
//            MakeInternetDialog();
//            return;
//        }
//        else {
            mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_daily_meal, R.id.nav_fav,
                    R.id.nav_my_cart, R.id.nav_order_history).setOpenableLayout(drawer).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
//        }

        Button button = findViewById(R.id.bt_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeLogOutDialog();
            }
        });

        LinearLayout ll = navigationView.getHeaderView(0).findViewById(R.id.header_ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        TextView name, email;
        name = navigationView.getHeaderView(0).findViewById(R.id.tv_userName_bar);
        email = navigationView.getHeaderView(0).findViewById(R.id.tv_userEmail_bar);

        SharedPreferences sharedPreferencesEmail = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String emailString = sharedPreferencesEmail.getString("email", "");

        LoginRoomDb roomDb = LoginRoomDb.getDatabase(this);
        name.setText(roomDb.loginDao().getUserName(emailString));
        email.setText(roomDb.loginDao().getUserEmail(emailString));
    }

    private void makeLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you really want to LogOut?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences("SignInPreference", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("signedIn", false);
                editor.apply();

                SharedPreferences sharedPreferencesCnt = getSharedPreferences("orderHistoryCount", Context.MODE_PRIVATE);
                int n = sharedPreferencesCnt.getInt("cnt", -1);
                SharedPreferences sharedPreferencesEmail = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
                String email = sharedPreferencesEmail.getString("email", "");

                LoginRoomDb loginRoomDb = LoginRoomDb.getDatabase(MainActivity.this);
                loginRoomDb.loginDao().setUserCount(n, email);

                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}