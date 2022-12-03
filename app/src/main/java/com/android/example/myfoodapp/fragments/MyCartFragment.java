package com.android.example.myfoodapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.example.myfoodapp.NetworkUtils;
import com.android.example.myfoodapp.activities.OrderConfirmed;
import com.android.example.myfoodapp.activities.ProfileActivity;
import com.android.example.myfoodapp.adapters.CartMealAdapter;
import com.android.example.myfoodapp.database.CartData;
import com.android.example.myfoodapp.database.CartRoomDb;
import com.android.example.myfoodapp.database.LoginRoomDb;
import com.android.example.myfoodapp.database.OrderHistoryData;
import com.android.example.myfoodapp.database.OrderHistoryRoomDb;
import com.android.example.myfoodapp.databinding.FragmentMyCartBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MyCartFragment extends Fragment implements CartMealAdapter.OnCheckListener {

    private ArrayList<CartData> mMealArrayList;
    private CartRoomDb mDatabase;
    private CartMealAdapter mAdapter;
    private FragmentMyCartBinding mBinding;
    private int mTax;

    public MyCartFragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentMyCartBinding.inflate(inflater, container, false);

        mMealArrayList = new ArrayList<>();

        mDatabase = CartRoomDb.getDatabase(getContext());
        mMealArrayList = (ArrayList<CartData>) mDatabase.cartDao().getAll();

        updatePrice();

        mAdapter = new CartMealAdapter(getContext(), mMealArrayList, mBinding.tvAmount, mBinding.tvActualAmount, mTax, this);

        mBinding.cartRv.setAdapter(mAdapter);
        mBinding.cartRv.setLayoutManager(new LinearLayoutManager(getContext()));


        mBinding.makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    MakeInternetDialog();
                    return;
                }

                OrderHistoryRoomDb orderHistoryRoomDb = OrderHistoryRoomDb.getDatabase(getContext());
                LoginRoomDb loginRoomDb = LoginRoomDb.getDatabase(getContext());
                SharedPreferences sharedPreferencesEmail = getContext().getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
                String email = sharedPreferencesEmail.getString("email", "");

                if (loginRoomDb.loginDao().getUserAddress(email) == null || loginRoomDb.loginDao().getUserPhoneNo(email) == null) {
                    makeDialog();
                    return;
                }

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("orderHistoryCount", Context.MODE_PRIVATE);

                int i = sharedPreferences.getInt("cnt", -1) + 1;

                String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

                for (int c = 0; c < mMealArrayList.size(); c++) {
                    Log.e("My Cart", "c - " + c);

                    OrderHistoryData orderHistoryData = new OrderHistoryData();
                    orderHistoryData.setSystemId(i);

                    orderHistoryData.setUserName(loginRoomDb.loginDao().getUserName(email));
                    orderHistoryData.setEmail(loginRoomDb.loginDao().getUserEmail(email));
                    orderHistoryData.setPassword(loginRoomDb.loginDao().getUserPassword(email));
                    orderHistoryData.setPhoneNo(loginRoomDb.loginDao().getUserPhoneNo(email));
                    orderHistoryData.setAddress(loginRoomDb.loginDao().getUserAddress(email));

                    orderHistoryData.setDate(currentDate);
                    orderHistoryData.setTime(currentTime);
                    orderHistoryData.setFoodId(mMealArrayList.get(c).getFoodId());
                    orderHistoryData.setName(mMealArrayList.get(c).getName());
                    orderHistoryData.setPrice(mMealArrayList.get(c).getPrice());
                    orderHistoryData.setQuantity(mMealArrayList.get(c).getQuantity());

                    orderHistoryRoomDb.orderHistoryDao().insert(orderHistoryData);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("cnt", i);
                editor.apply();

                startActivity(new Intent(getContext(), OrderConfirmed.class));
            }
        });


        onClick();

        return mBinding.getRoot();
    }

    private void makeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Attention!");
        builder.setMessage("Please fill Address and Phone Number!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void MakeInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Internet Connection");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    MakeInternetDialog();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    void updatePrice() {
        int sum = 0;
        for (int i = 0; i < mMealArrayList.size(); i++) {
            sum += mMealArrayList.get(i).getQuantity() * mMealArrayList.get(i).getPrice();
        }

        mBinding.tvAmount.setText(" " + sum);
        Log.e("Cart Value - ", "" + sum);

        int[] taxes = new int[4];
        taxes[0] = 20;
        taxes[1] = 25;
        taxes[2] = 30;
        taxes[3] = 35;
        mTax = taxes[new Random().nextInt(taxes.length)];
        mBinding.tvTaxes.setText("" + mTax);

        mBinding.tvActualAmount.setText("" + (mTax + sum));
    }

    @Override
    public void onClick() {
        if (mMealArrayList.isEmpty()) {
            //Empty
            mBinding.tvPermanent.setVisibility(View.GONE);
            mBinding.view1.setVisibility(View.GONE);
            mBinding.cvCart.setVisibility(View.GONE);
            mBinding.rlBelowCartCv.setVisibility(View.GONE);

            mBinding.ivEmptyCart.setVisibility(View.VISIBLE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
            mBinding.tvEmpty1.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvPermanent.setVisibility(View.VISIBLE);
            mBinding.view1.setVisibility(View.VISIBLE);
            mBinding.cvCart.setVisibility(View.VISIBLE);
            mBinding.rlBelowCartCv.setVisibility(View.VISIBLE);

            mBinding.ivEmptyCart.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
            mBinding.tvEmpty1.setVisibility(View.GONE);
        }
    }
}