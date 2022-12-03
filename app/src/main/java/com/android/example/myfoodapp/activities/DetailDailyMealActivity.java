package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.example.myfoodapp.NetworkUtils;
import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.DetailDailyMealAdapter;
import com.android.example.myfoodapp.databinding.ActivityDetailDailyMealBinding;
import com.android.example.myfoodapp.models.DetailDailyMeal;

import java.util.ArrayList;

public class DetailDailyMealActivity extends AppCompatActivity {

    private DetailDailyMealAdapter mAdapter;
    private ArrayList<DetailDailyMeal> mList;
    private ActivityDetailDailyMealBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_daily_meal);

        //If network is not available
        if(!NetworkUtils.isNetworkAvailable(this)) {
            MakeInternetDialog();
            mBinding.coordinatorLayout.setVisibility(View.GONE);
            mBinding.pbDetailDaily.setVisibility(View.VISIBLE);
        }
        else {
            mBinding.coordinatorLayout.setVisibility(View.VISIBLE);
            mBinding.pbDetailDaily.setVisibility(View.GONE);
        }

        String name = getIntent().getStringExtra("MealName");
        mBinding.collapsingToolbar.setTitle(name);
        int image = getIntent().getIntExtra("MealImage", - 1);
        if(image == -1)
            mBinding.detailIv.setImageResource(R.drawable.pizza);
        else
            mBinding.detailIv.setImageResource(image);

        mList = new ArrayList<>();
        String meal = getIntent().getStringExtra("MealName");
        switch (meal) {
            case "Breakfast":
                            mList.add(new DetailDailyMeal("breakfast_1", "fav1", "Omelette", "Fresh with onions and capsicum", 75, (float)4.1));
                            mList.add(new DetailDailyMeal("breakfast_2", "fav2", "Light Burger", "Diet patty with a italian sauce", 139, (float)3.9));
                            mList.add(new DetailDailyMeal("breakfast_3", "fav3", "Noodles", "A wide taste of fresh vegetables with green sc.", 89, (float)4.0));
                            break;

            case "Lunch":
                            mList.add(new DetailDailyMeal("lunch_1", "lunch1", "Patty Masala", "Combination of two masala patty with veg.", 169, (float)3.5));
                            mList.add(new DetailDailyMeal("lunch_2", "lunch2", "Chole Chawal", "Fresh Chole's with boiled rice", 99, (float)3.8));
                            mList.add(new DetailDailyMeal("lunch_3", "lunch3", "Chole Bathure", "Chana masala with deep-fried bread made from maida.", 89, (float)4.3));
                            mList.add(new DetailDailyMeal("lunch_4", "lunch4", "White Sauce Pasta", "Made with italian seasoning, olive and capsicum", 130, (float)4.4));
                            break;

            case "Dinner":
                            mList.add(new DetailDailyMeal("dinner_1", "dinner1", "Special Veg Thali", "Shai Paneer, Dal Makhni, 1 rasgulla, 3 Naans with rice", 249, (float)4.2));
                            mList.add(new DetailDailyMeal("dinner_2", "dinner2", "Kathi Rolls", "2 Rolls filled with Paneer Tikka and delicious sauces", 220, (float)3.8));
                            mList.add(new DetailDailyMeal("dinner_3", "dinner3", "Dosa Sambar", "Masala dosa, sambar, with chutneys", 199, (float)3.6));
                            break;

            case "Sweets":
                            mList.add(new DetailDailyMeal("sweets_1", "s1", "Chocolates Shots", "Different color of chocolates", 35, (float)4.1));
                            mList.add(new DetailDailyMeal("sweets_2", "s2", "Donuts", "3 pcs. with chocolate gems", 69, (float)4.0));
                            mList.add(new DetailDailyMeal("sweets_3", "s3", "Ice Cream", "Strawberry with Vanilla", 39, (float)4.3));
                            break;

            case "Coffee":
                            mList.add(new DetailDailyMeal("coffee_1", "coffee1", "Brown Coffee", "", 120, (float)4.2));
                            mList.add(new DetailDailyMeal("coffee_2", "coffee2", "Cappuccino Coffee", "Milk, espresso and foamed milk", 129, (float)4.2));
                            mList.add(new DetailDailyMeal("coffee_3", "coffee3", "Americano Coffee", "Espresso and Hot water", 110, (float)4.3));
                            break;

            default:
                /*
                    Nothing to do!!!!
                */
                break;
        }

        mAdapter = new DetailDailyMealAdapter(this, mList);

        mBinding.detailRv.setAdapter(mAdapter);
        mBinding.detailRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void MakeInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailDailyMealActivity.this);
        builder.setTitle("No Internet Connection");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!NetworkUtils.isNetworkAvailable(DetailDailyMealActivity.this)){
                    MakeInternetDialog();
                }
                else {
                    mBinding.pbDetailDaily.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.coordinatorLayout.setVisibility(View.VISIBLE);
                            mBinding.pbDetailDaily.setVisibility(View.GONE);
                        }
                    }, 2000);

                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }
}