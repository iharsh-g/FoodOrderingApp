package com.android.example.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.activities.OrderHistoryViewDetailsActivity;
import com.android.example.myfoodapp.database.OrderHistoryData;
import com.android.example.myfoodapp.database.OrderHistoryRoomDb;
import com.android.example.myfoodapp.models.OrderHistory;
import com.android.example.myfoodapp.models.OrderHistoryDetails;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private Context mContext;
    private ArrayList<OrderHistory> mList;
    private OrderHistoryRoomDb mOrderHistoryRoomDb;

    public OrderHistoryAdapter(Context mContext, ArrayList<OrderHistory> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_order_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        mOrderHistoryRoomDb = OrderHistoryRoomDb.getDatabase(mContext);
        SharedPreferences sharedPreferencesEmail = mContext.getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");

        OrderHistory currData = mList.get(position);

        holder.mDate.setText(currData.getDate() + " at " + currData.getTime());
        holder.mAmount.setText("" + currData.getPrice());

        ArrayList<OrderHistoryData> foodList = (ArrayList<OrderHistoryData>) mOrderHistoryRoomDb.orderHistoryDao().getDetails(currData.getI(), email);
        ArrayList<OrderHistoryDetails> ArrayList = new ArrayList<>();

        //Adding Names of the food Items
        for(int c = 0; c < foodList.size(); c += 1) {
            ArrayList.add(new OrderHistoryDetails(foodList.get(c).getName(), foodList.get(c).getQuantity()));
        }

        OrderHistoryDetailsAdapter adapter = new OrderHistoryDetailsAdapter(mContext, ArrayList);
        holder.mRv.setAdapter(adapter);
        holder.mRv.setLayoutManager(new LinearLayoutManager(mContext));

        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderHistoryViewDetailsActivity.class);
                intent.putExtra("ith value", currData.getI());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView mDate, mAmount;
        private Button mDetails;
        private RecyclerView mRv;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mDate = itemView.findViewById(R.id.tv_order_time);
            mAmount = itemView.findViewById(R.id.items_total_amount);
            mDetails = itemView.findViewById(R.id.bt_view_details);
            mRv = itemView.findViewById(R.id.rv_items);
        }
    }
}
