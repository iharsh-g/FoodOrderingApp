package com.android.example.myfoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.models.OrderHistoryDetails;

import java.util.ArrayList;

public class OrderHistoryDetailsAdapter extends RecyclerView.Adapter<OrderHistoryDetailsAdapter.OrderHistoryDetailsViewHolder> {

    private Context mContext;
    private ArrayList<OrderHistoryDetails> mList;


    public OrderHistoryDetailsAdapter(Context mContext, ArrayList<OrderHistoryDetails> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public OrderHistoryDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryDetailsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_order_history_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryDetailsViewHolder holder, int position) {
        OrderHistoryDetails curr = mList.get(position);

        holder.mFoodName.setText(curr.getQuantity() + " x " + curr.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OrderHistoryDetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView mFoodName;
        public OrderHistoryDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            mFoodName = itemView.findViewById(R.id.tv_item_name);
        }
    }
}
