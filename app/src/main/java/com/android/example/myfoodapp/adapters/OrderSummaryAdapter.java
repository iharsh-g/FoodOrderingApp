package com.android.example.myfoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.CartData;
import com.android.example.myfoodapp.database.CartRoomDb;

import java.util.ArrayList;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {
    private Context mContext;
    private ArrayList<CartData> mList;

    public OrderSummaryAdapter(Context mContext, ArrayList<CartData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderSummaryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_order_summary, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, int position) {
        CartData cartData = mList.get(position);

        holder.mName.setText("" + cartData.getName() + "(" + cartData.getQuantity() + ")");
        holder.mPrice.setText("" + cartData.getPrice());
        holder.mAmount.setText("" + cartData.getPrice() * cartData.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mAmount, mPrice;
        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_meal_summary);
            mPrice = itemView.findViewById(R.id.tv_price_summary);
            mAmount = itemView.findViewById(R.id.tv_amount_summary);
        }
    }
}
