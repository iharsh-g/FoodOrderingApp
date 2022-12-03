package com.android.example.myfoodapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.database.CartData;
import com.android.example.myfoodapp.database.CartRoomDb;
import com.android.example.myfoodapp.database.FavouritesData;
import com.android.example.myfoodapp.database.FavouritesRoomDb;
import com.android.example.myfoodapp.models.DetailDailyMeal;

import java.util.ArrayList;

public class DetailDailyMealAdapter extends RecyclerView.Adapter<DetailDailyMealAdapter.DetailDailyMealViewHolder> {

    private Context mContext;
    private ArrayList<DetailDailyMeal> mList;
    private CartRoomDb mDatabase;

    public DetailDailyMealAdapter(Context mContext, ArrayList<DetailDailyMeal> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DetailDailyMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailDailyMealViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_detail_daily_meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailDailyMealViewHolder holder, int position) {
        DetailDailyMeal curr = mList.get(position);
        mDatabase = CartRoomDb.getDatabase(mContext);
        SharedPreferences sharedPreferencesEmail = mContext.getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");

        String s = curr.getDetailImage();
        int id = mContext.getResources().getIdentifier(s, "drawable", mContext.getPackageName());
        holder.mIv.setImageResource(id);

        holder.mDetailMealName.setText(curr.getDetailMealName());
        holder.mDes.setText(curr.getDetailMealDes());
        holder.mRatingBar.setRating(curr.getRating());
        holder.mRating.setText("" + curr.getRating());
        holder.mPrice.setText("" + curr.getPrice());

        if(isInFav(email, curr.getMealId())) {
            holder.mFav.setImageResource(R.drawable.ic_filled_favorite);
        }
        else {
            holder.mFav.setImageResource(R.drawable.ic_favorite_border);
        }

        //Visibility of button of added to cart or not
        if(mDatabase.cartDao().isRowExist(curr.getMealId())) {
            Log.e("DetailDaily", "Row Exist both should be visible");
            holder.mButtonAdded.setVisibility(View.VISIBLE);
            holder.mImgButton.setVisibility(View.VISIBLE);
            holder.mButtonForAdd.setVisibility(View.GONE);
        }
        else {
            Log.e("DetailDaily", "Row not Exist 1 button visible");
            holder.mButtonForAdd.setVisibility(View.VISIBLE);
            holder.mButtonAdded.setVisibility(View.GONE);
            holder.mImgButton.setVisibility(View.GONE);
        }

        //If User Add Item to the Cart
        holder.mButtonForAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartData data = new CartData();
                data.setFoodId(curr.getMealId());
                data.setName(curr.getDetailMealName());
                data.setPrice(curr.getPrice());
                data.setQuantity(1);
                data.setFoodImage(s);

                mDatabase.cartDao().insert(data);
                Toast.makeText(mContext, "Item Added", Toast.LENGTH_SHORT).show();

                //Visibility Changes
                holder.mButtonAdded.setVisibility(View.VISIBLE);
                holder.mImgButton.setVisibility(View.VISIBLE);
                holder.mButtonForAdd.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });

        //If User Delete Item from Cart
        holder.mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.cartDao().delete(curr.getMealId());
                Toast.makeText(mContext, "Item Deleted", Toast.LENGTH_SHORT).show();

                //Visibility Changes
                holder.mButtonForAdd.setVisibility(View.VISIBLE);
                holder.mButtonAdded.setVisibility(View.GONE);
                holder.mImgButton.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });

        holder.mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInFav( email, curr.getMealId() )){
                    //Removing
                    FavouritesRoomDb favouritesRoomDb = FavouritesRoomDb.getDatabase(mContext);
                    favouritesRoomDb.favouritesDao().delete(email, curr.getMealId());
                    holder.mFav.setImageResource(R.drawable.ic_favorite_border);
                    notifyDataSetChanged();
                }
                else {
                    //Adding
                    addFav(email, curr.getMealId(), curr.getDetailMealName(), curr.getDetailMealDes(),
                            curr.getDetailImage(), curr.getPrice(), curr.getRating());
                    holder.mFav.setImageResource(R.drawable.ic_filled_favorite);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DetailDailyMealViewHolder extends RecyclerView.ViewHolder {
        private TextView mDes, mDetailMealName, mRating, mPrice;
        private ImageView mIv, mFav;
        private RatingBar mRatingBar;
        private AppCompatButton mButtonForAdd, mButtonAdded;   //buttonForAdd is used when user add the meal in cart
        private ImageButton mImgButton;

        public DetailDailyMealViewHolder(@NonNull View itemView) {
            super(itemView);

            mFav = itemView.findViewById(R.id.iv_addFav);
            mIv = itemView.findViewById(R.id.iv_detail_daily_meal);
            mDetailMealName = itemView.findViewById(R.id.detail_daily_meal_name);
            mDes = itemView.findViewById(R.id.detail_daily_meal_des);
            mRatingBar = itemView.findViewById(R.id.detail_daily_meal_row_rating);
            mRating = itemView.findViewById(R.id.detail_daily_meal_rating);
            mPrice = itemView.findViewById(R.id.detail_daily_meal_price);

            mButtonForAdd = itemView.findViewById(R.id.bt_add);
            mButtonAdded = itemView.findViewById(R.id.bt_added_cart);
            mImgButton = itemView.findViewById(R.id.iv_delete_item);
        }
    }

    boolean isInFav(String mail, String id){
        FavouritesRoomDb favouritesRoomDb = FavouritesRoomDb.getDatabase(mContext);
        return favouritesRoomDb.favouritesDao().isExist(mail, id);
    }

    private void addFav(String email, String foodId, String foodName, String foodDes, String foodImage, float price, float rating) {
        FavouritesData favouritesData = new FavouritesData();
        favouritesData.setEmail(email);
        favouritesData.setFoodId(foodId);
        favouritesData.setName(foodName);
        favouritesData.setDes(foodDes);
        favouritesData.setImage(foodImage);
        favouritesData.setPrice(price);
        favouritesData.setRating(rating);

        FavouritesRoomDb favouritesRoomDb = FavouritesRoomDb.getDatabase(mContext);
        favouritesRoomDb.favouritesDao().insert(favouritesData);
    }
}
