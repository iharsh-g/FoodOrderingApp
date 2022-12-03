package com.android.example.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.CartData;
import com.android.example.myfoodapp.database.CartRoomDb;

import java.util.ArrayList;

public class CartMealAdapter extends RecyclerView.Adapter<CartMealAdapter.CartMealViewHolder> {

    private Context mContext;
    private ArrayList<CartData> mList;
    private CartRoomDb cartRoomDb;
    private TextView mAmount, mActualAmount;
    private int mTax;
    public OnCheckListener mListener;

    public CartMealAdapter(Context mContext, ArrayList<CartData> mList, TextView textView, TextView textView1, int tax, OnCheckListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mAmount = textView;
        this.mActualAmount = textView1;
        this.mTax = tax;
        this.mListener = listener;
    }

    public interface OnCheckListener {
        void onClick();
    }

    @NonNull
    @Override
    public CartMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartMealViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_my_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartMealViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cartRoomDb = CartRoomDb.getDatabase(mContext);
        CartData curr = mList.get(position);

        holder.mActualPrice.setText("" + curr.getPrice());
        holder.mPrice.setText("" + curr.getPrice() * curr.getQuantity());
        holder.mName.setText(curr.getName());
        holder.mQuantity.setText("" + curr.getQuantity());

        String s = curr.getFoodImage();
        int id = mContext.getResources().getIdentifier(s, "drawable", mContext.getPackageName());
        holder.imageView.setImageResource(id);

        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = mList.get(position).getQuantity();
                val++;

                mList.get(position).setQuantity(val);
                holder.mPrice.setText("" + curr.getPrice() * val);
                cartRoomDb.cartDao().updateQuantity(val, curr.getFoodId());

                notifyDataSetChanged();
                updatePrice();
            }
        });

        holder.mSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = mList.get(position).getQuantity();
                if(val == 1) {
                    makeDialog(curr.getFoodId(), position);
                    return;
                }

                val--;
                mList.get(position).setQuantity(val);
                holder.mPrice.setText("" + curr.getPrice() * val);
                cartRoomDb.cartDao().updateQuantity(val, curr.getFoodId());

                notifyDataSetChanged();
                updatePrice();
            }
        });
    }

    void updatePrice() {
        int sum = 0;
        for(int i = 0; i < mList.size(); i++) {
            sum += mList.get(i).getQuantity() * mList.get(i).getPrice();
        }

        mAmount.setText("" + sum);
        mActualAmount.setText("" + (sum + mTax));
    }

    void makeDialog(String id, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Do you really want to delete this item?");
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartRoomDb.cartDao().delete(id);
                Toast.makeText(mContext, "Item Deleted", Toast.LENGTH_SHORT).show();
                mList.remove(pos);
                notifyDataSetChanged();

                mListener.onClick();
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CartMealViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView mName, mPrice, mQuantity, mActualPrice;
        private Button mAdd, mSub;

        public CartMealViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cart_meal);
            mName = itemView.findViewById(R.id.tv_cart_meal_name);
            mPrice = itemView.findViewById(R.id.cart_meal_price);
            mActualPrice = itemView.findViewById(R.id.tv_actual_price);
            mAdd = itemView.findViewById(R.id.bt_forAdd);
            mSub = itemView.findViewById(R.id.bt_forSub);

            mQuantity = itemView.findViewById(R.id.cart_meal_quantity);
        }
    }
}
