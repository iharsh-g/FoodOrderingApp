package com.android.example.myfoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.activities.DetailDailyMealActivity;
import com.android.example.myfoodapp.models.DailyMeal;

import java.util.ArrayList;

public class DailyMealAdapter extends RecyclerView.Adapter<DailyMealAdapter.DailyMealViewHolder> {

    private Context mContext;
    private ArrayList<DailyMeal> mList;

    public DailyMealAdapter(Context mContext, ArrayList<DailyMeal> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DailyMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyMealViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_daily_meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMealViewHolder holder, int position) {
        DailyMeal curr = mList.get(position);

        holder.mIv.setImageResource(curr.getMealImage());
        holder.mMealName.setText(curr.getMealName());
        holder.mDis.setText("" + curr.getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailDailyMealActivity.class);
                intent.putExtra("MealName", curr.getMealName());
                intent.putExtra("MealImage", curr.getMealImage());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DailyMealViewHolder extends RecyclerView.ViewHolder {
        private TextView mDis, mMealName;
        private ImageView mIv;

        public DailyMealViewHolder(@NonNull View itemView) {
            super(itemView);

            mMealName = itemView.findViewById(R.id.tv_daily_meal);
            mDis = itemView.findViewById(R.id.tv_discount);
            mIv = itemView.findViewById(R.id.iv_daily_meal);
        }
    }
}
