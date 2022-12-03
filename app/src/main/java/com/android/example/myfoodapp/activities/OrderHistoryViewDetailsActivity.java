package com.android.example.myfoodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.OrderHistoryViewDetailsAdapter;
import com.android.example.myfoodapp.database.OrderHistoryData;
import com.android.example.myfoodapp.database.OrderHistoryRoomDb;

import java.util.ArrayList;

public class OrderHistoryViewDetailsActivity extends AppCompatActivity {

    private ArrayList<OrderHistoryData> mList;
    private OrderHistoryViewDetailsAdapter mAdapter;
    private RecyclerView mRv;
    private OrderHistoryRoomDb mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_view_details);
        mDatabase = OrderHistoryRoomDb.getDatabase(this);

        int i = getIntent().getIntExtra("ith value", -1);

        if(i == -1) { return; }

        SharedPreferences sharedPreferencesEmail = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");
        TextView textViewAddress = findViewById(R.id.tv_address);
        textViewAddress.setText(mDatabase.orderHistoryDao().getAddress(email));

        mList = new ArrayList<>();

        mList = (ArrayList<OrderHistoryData>) mDatabase.orderHistoryDao().getDetails(i, email);

        TextView mDate, mTime, mAmount;
        mDate = findViewById(R.id.tv_date);
        mTime = findViewById(R.id.tv_time);
        mAmount = findViewById(R.id.tv_amount_details);

        mAdapter = new OrderHistoryViewDetailsAdapter(this, mList, mDate, mTime);

        mRv = findViewById(R.id.rv_details);

        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(this));

        int sum = 0;
        for(int c = 0; c < mList.size(); c++) {
            sum += mList.get(c).getQuantity() * mList.get(c).getPrice();
        }

        mAmount.setText("" + sum);
    }
}