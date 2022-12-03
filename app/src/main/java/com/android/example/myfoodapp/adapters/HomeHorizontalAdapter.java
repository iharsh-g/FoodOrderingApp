package com.android.example.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.models.HomeHorizontal;
import com.android.example.myfoodapp.models.HomeVertical;

import java.util.ArrayList;

public class HomeHorizontalAdapter extends RecyclerView.Adapter<HomeHorizontalAdapter.HomeHorizontalViewHolder> {

    private Context mContext;
    private ArrayList<HomeHorizontal> mList;
    private UpdateVerticalRecyclerView updateVerticalRecyclerView;

    boolean check = true, select = true;
    int row_index = -1;

    public HomeHorizontalAdapter(Context mContext, ArrayList<HomeHorizontal> mList, UpdateVerticalRecyclerView updateVerticalRecyclerView) {
        this.mContext = mContext;
        this.mList = mList;
        this.updateVerticalRecyclerView = updateVerticalRecyclerView;
    }

    @NonNull
    @Override
    public HomeHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeHorizontalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_fragment_home_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHorizontalViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeHorizontal curr = mList.get(position);

        holder.mIv.setImageResource(curr.getImage());
        holder.mTv.setText(curr.getName());

        if (check) {
            ArrayList<HomeVertical> homeVerticals = new ArrayList<>();
            homeVerticals.add(new HomeVertical("pizza_1","pizza1", (float) 3.8, "Capsicum and Onion Pizza", "10:00 - 23:00", 249));
            homeVerticals.add(new HomeVertical("pizza_2","pizza2", (float) 4.5, "Special Delighted Cheese Pan Pizza", "10:00 - 23:00", 349));
            homeVerticals.add(new HomeVertical("pizza_3","pizza3", (float) 4.2, "Mix Vegetable Pizza", "10:00 - 23:00", 299));
            homeVerticals.add(new HomeVertical("pizza_4","pizza4", (float) 4.1, "Corn Delighted Pan Pizza", "10:00 - 23:00", 399));

            updateVerticalRecyclerView.callBack(position, homeVerticals);
            check = false;
        }

        holder.mCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();

                switch (position) {
                    case 0: {
                        ArrayList<HomeVertical> homeVerticals = new ArrayList<>();
                        homeVerticals.add(new HomeVertical("pizza_1","pizza1", (float) 3.8, "Capsicum and Onion Pizza", "10:00 - 23:00", 249));
                        homeVerticals.add(new HomeVertical("pizza_2","pizza2", (float) 4.5, "Special Delighted Cheese Pan Pizza", "10:00 - 23:00", 349));
                        homeVerticals.add(new HomeVertical("pizza_3","pizza3", (float) 4.2, "Mix Vegetable Pizza", "10:00 - 23:00", 299));
                        homeVerticals.add(new HomeVertical("pizza_4","pizza4", (float) 4.1, "Corn Delighted Pan Pizza", "10:00 - 23:00", 399));

                        updateVerticalRecyclerView.callBack(position, homeVerticals);
                        break;
                    }

                    case 1: {
                        ArrayList<HomeVertical> homeVerticals = new ArrayList<>();
                        homeVerticals.add(new HomeVertical("burger_3","burger3", (float) 4.2, "Mc Maharaja with delicious sauce", "10:00 - 23:00", 249));
                        homeVerticals.add(new HomeVertical("burger_2","burger2", (float) 3.5, "Mc Crispy", "10:00 - 23:00", 59));
                        homeVerticals.add(new HomeVertical("burger_1","burger1", (float) 4.2, "Mc Double Chicken Patty", "10:00 - 23:00", 349));
                        homeVerticals.add(new HomeVertical("burger_4","burger4", (float) 4.1, "Mc Whoofer with chezze", "10:00 - 23:00", 299));

                        updateVerticalRecyclerView.callBack(position, homeVerticals);
                        break;
                    }

                    case 2: {
                        ArrayList<HomeVertical> homeVerticals = new ArrayList<>();
                        homeVerticals.add(new HomeVertical("fries_1","fries1", (float) 3.8, "Hand Crushed Thin Fries", "10:00 - 23:00", 139));
                        homeVerticals.add(new HomeVertical("fries_2","fries2", (float) 4.2, "Potato Fries with delicious seasoning", "10:00 - 23:00", 129));
                        homeVerticals.add(new HomeVertical("fries_3","fries3", (float) 4.2, "Honey Fries", "10:00 - 23:00", 149));
                        homeVerticals.add(new HomeVertical("fries_4","fries4", (float) 3.5, "Potato Fries", "10:00 - 23:00", 99));

                        updateVerticalRecyclerView.callBack(position, homeVerticals);
                        break;
                    }

                    case 3: {
                        ArrayList<HomeVertical> homeVerticals = new ArrayList<>();
                        homeVerticals.add(new HomeVertical("icecream_1","icecream1", (float) 4.2, "Chocolate Ice Cream", "10:00 - 23:00", 149));
                        homeVerticals.add(new HomeVertical("icecream_2","icecream2", (float) 4.0, "Butterscotch Ice Cream", "10:00 - 23:00", 129));
                        homeVerticals.add(new HomeVertical("icecream_3","icecream3", (float) 4.4, "Mango Ice Cream with delicious choco lining", "10:00 - 23:00", 199));
                        homeVerticals.add(new HomeVertical("icecream_4","icecream4", (float) 4.1, "Special Tutty Frooty Ice Cream", "10:00 - 23:00", 135));

                        updateVerticalRecyclerView.callBack(position, homeVerticals);
                        break;
                    }

                    case 4: {
                        ArrayList<HomeVertical> homeVerticals = new ArrayList<>();
                        homeVerticals.add(new HomeVertical("sandwich_1","sandwich1", (float) 3.5, "Tandoori Sandwich", "10:00 - 23:00", 99));
                        homeVerticals.add(new HomeVertical("sandwich_2","sandwich2", (float) 4.1, "Vegetable Sandwich", "10:00 - 23:00", 79));
                        homeVerticals.add(new HomeVertical("sandwich_3","sandwich3", (float) 4.2, "Chicken Sandwich", "10:00 - 23:00", 139));
                        homeVerticals.add(new HomeVertical("sandwich_4","sandwich4", (float) 4.3, "Paneer Sandwich with small fries", "10:00 - 23:00", 199));

                        updateVerticalRecyclerView.callBack(position, homeVerticals);
                        break;
                    }
                } /* *** End of Switch *** */
            }
        }); /* End of click listener */

        if (select) {
            if (position == 0) {
                holder.mCv.setBackgroundResource(R.drawable.change_bg);
                select = false;
            }
        }
        else {
            if (row_index == position) {
                holder.mCv.setBackgroundResource(R.drawable.change_bg);
            }
            else {
                holder.mCv.setBackgroundResource(R.drawable.default_bg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HomeHorizontalViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIv;
        private TextView mTv;
        private CardView mCv;

        public HomeHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.list_item_iv);
            mTv = itemView.findViewById(R.id.list_item_tv);
            mCv = itemView.findViewById(R.id.cv);
        }
    }
}
