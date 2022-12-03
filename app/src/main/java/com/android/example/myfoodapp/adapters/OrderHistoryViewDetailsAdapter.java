package com.android.example.myfoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.OrderHistoryData;

import java.util.ArrayList;

public class OrderHistoryViewDetailsAdapter extends RecyclerView.Adapter<OrderHistoryViewDetailsAdapter.OrderHistoryViewDetailsViewHolder> {

    private Context mContext;
    private ArrayList<OrderHistoryData> mList;
    private TextView mDate, mTime;

    public OrderHistoryViewDetailsAdapter(Context mContext, ArrayList<OrderHistoryData> mList, TextView mDate, TextView mTime) {
        this.mContext = mContext;
        this.mList = mList;
        this.mDate = mDate;
        this.mTime = mTime;
    }

    @NonNull
    @Override
    public OrderHistoryViewDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryViewDetailsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_order_summary, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewDetailsViewHolder holder, int position) {
        OrderHistoryData cartData = mList.get(position);

        mDate.setText(cartData.getDate());
        mTime.setText(cartData.getTime());
        holder.mName.setText("" + cartData.getName() + "(" + cartData.getQuantity() + ")");
        holder.mPrice.setText("" + cartData.getPrice());
        holder.mAmount.setText("" + cartData.getPrice() * cartData.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OrderHistoryViewDetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mAmount, mPrice;

        public OrderHistoryViewDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_meal_summary);
            mPrice = itemView.findViewById(R.id.tv_price_summary);
            mAmount = itemView.findViewById(R.id.tv_amount_summary);
        }
    }
}
