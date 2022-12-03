package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.example.myfoodapp.MainActivity;
import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.OrderSummaryAdapter;
import com.android.example.myfoodapp.database.CartData;
import com.android.example.myfoodapp.database.CartRoomDb;
import com.android.example.myfoodapp.databinding.ActivityOrderConfirmedBinding;

import java.util.ArrayList;

public class OrderConfirmed extends AppCompatActivity {

    private ActivityOrderConfirmedBinding mBinding;
    private ArrayList<CartData> mList;
    private OrderSummaryAdapter mAdapter;
    private RecyclerView mRv;
    private CartRoomDb mDatabase;

    //In all postDelayed function the last value like -> 1000, 7000, 8000 and 11000 are the seconds count when the activity starts (1sec, 7sec, 8sec, 11sec)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_confirmed);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);

        mBinding.lottie1.animate();
        mBinding.sit.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.sit.setVisibility(View.VISIBLE);
                mBinding.sit.setAnimation(animation);
            }
        }, 1000);

        mBinding.RlCenter.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.RlCenter.setVisibility(View.GONE);
            }
        }, 7000);

        mBinding.lottie.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.lottie.setVisibility(View.VISIBLE);
                mBinding.lottie.animate().translationY(-2000).setDuration(2000).setStartDelay(2900);

                //Here 1500 is like when it is 8sec then after 1.5second which is 9.5sec this will start
                mBinding.orderRec.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.orderRec.setVisibility(View.VISIBLE);
                    }
                }, 1500);
                mBinding.orderRec.animate().translationY(-2000).setDuration(2000).setStartDelay(2900);
            }
        }, 8000);

        mList = new ArrayList<>();

        mDatabase = CartRoomDb.getDatabase(this);
        mList = (ArrayList<CartData>) mDatabase.cartDao().getAll();

        mAdapter = new OrderSummaryAdapter(this, mList);

        CardView cardView = findViewById(R.id.Cv);
        cardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.VISIBLE);
                cardView.startAnimation(animation);
                cardView.animate().translationY(-500).setDuration(2000);
            }
        }, 11000);


        mRv = findViewById(R.id.rv_summary);

        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(this));

        TextView mAmount = findViewById(R.id.tv_amount_summary);
        Log.e("Cart Value - ", "" + mDatabase.cartDao().SumOfPrice());
        mAmount.setText("" + mDatabase.cartDao().SumOfPrice());

        mDatabase.cartDao().reset(mList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderConfirmed.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}