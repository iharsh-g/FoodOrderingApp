package com.android.example.myfoodapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.example.myfoodapp.MainActivity;
import com.android.example.myfoodapp.NetworkUtils;
import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.OrderHistoryAdapter;
import com.android.example.myfoodapp.database.OrderHistoryData;
import com.android.example.myfoodapp.database.OrderHistoryRoomDb;
import com.android.example.myfoodapp.models.OrderHistory;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment {

    private ArrayList<OrderHistory> mList;
    private OrderHistoryAdapter mAdapter;
    private OrderHistoryRoomDb roomDb;
    private RecyclerView mRv;
    private ProgressBar mPb;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_order_history, container, false);

        mRv = root.findViewById(R.id.rv);
        mPb = root.findViewById(R.id.pb_history);

        SharedPreferences sharedPreferencesEmail = getContext().getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");

        if(!NetworkUtils.isNetworkAvailable(getContext())){
            MakeInternetDialog();
            mRv.setVisibility(View.GONE);
            mPb.setVisibility(View.VISIBLE);
        }
        else {
            mRv.setVisibility(View.VISIBLE);
            mPb.setVisibility(View.GONE);
        }

        mList = new ArrayList<>();

        roomDb = OrderHistoryRoomDb.getDatabase(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("orderHistoryCount", Context.MODE_PRIVATE);
        int n = sharedPreferences.getInt("cnt", -1);

        //To show latest order at top
        for(int i = n; i >= 0; i--) {
            ArrayList<OrderHistoryData> list = (ArrayList<OrderHistoryData>) roomDb.orderHistoryDao().getDetails(i, email);
            mList.add(new OrderHistory(list.get(0).getDate(), list.get(0).getTime(), roomDb.orderHistoryDao().getTotalAmount(i, email), i));
        }

        mAdapter = new OrderHistoryAdapter(getContext(), mList);

        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    private void MakeInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Internet Connection");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!NetworkUtils.isNetworkAvailable(getContext())){
                    MakeInternetDialog();
                }
                else {
                    mPb.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRv.setVisibility(View.VISIBLE);
                            mPb.setVisibility(View.GONE);
                        }
                    }, 1000);

                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}