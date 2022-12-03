package com.android.example.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private ArrayList<FavouritesData> mList;
    private Context mContext;
    private OnItemListener mListener;

    public FavouritesAdapter(ArrayList<FavouritesData> mList, Context mContext, OnItemListener mListener) {
        this.mList = mList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    public interface OnItemListener {
        void onClick();
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouritesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_favourites_meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FavouritesData curr = mList.get(position);
        CartRoomDb cartRoomDb = CartRoomDb.getDatabase(mContext);

        String s = curr.getImage();
        int id = mContext.getResources().getIdentifier(s, "drawable", mContext.getPackageName());
        holder.mIv.setImageResource(id);

        holder.mMealName.setText(curr.getName());
        holder.mDes.setText(curr.getDes());
        holder.mRatingBar.setRating(curr.getRating());
        holder.mRating.setText("" + curr.getRating());
        holder.mPrice.setText("" + curr.getPrice());

        if(cartRoomDb.cartDao().isRowExist(curr.getFoodId())) {
//            Log.e("DetailDaily", "Row Exist both should be visible");
            holder.mButtonAdded.setVisibility(View.VISIBLE);
            holder.mImgButton.setVisibility(View.VISIBLE);
            holder.mButtonForAdd.setVisibility(View.GONE);
        }
        else {
//            Log.e("DetailDaily", "Row not Exist 1 button visible");
            holder.mButtonForAdd.setVisibility(View.VISIBLE);
            holder.mButtonAdded.setVisibility(View.GONE);
            holder.mImgButton.setVisibility(View.GONE);
        }

        //If User Add Item to the Cart
        holder.mButtonForAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartData data = new CartData();
                data.setFoodId(curr.getFoodId());
                data.setName(curr.getName());
                data.setPrice(curr.getPrice());
                data.setQuantity(1);
                data.setFoodImage(s);

                cartRoomDb.cartDao().insert(data);
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
                cartRoomDb.cartDao().delete(curr.getFoodId());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Do you want to remove this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FavouritesRoomDb favouritesRoomDb = FavouritesRoomDb.getDatabase(mContext);
                        favouritesRoomDb.favouritesDao().delete(curr.getEmail(), curr.getFoodId());
                        mList.remove(position);
                        notifyDataSetChanged();
                        mListener.onClick();

                        Toast.makeText(mContext, "Item Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class FavouritesViewHolder extends RecyclerView.ViewHolder {
        private TextView mDes, mMealName, mRating, mPrice;
        private ImageView mIv, mFav;
        private RatingBar mRatingBar;
        private AppCompatButton mButtonForAdd, mButtonAdded;   //buttonForAdd is used when user add the meal in cart
        private ImageButton mImgButton;

        public FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);

            mFav = itemView.findViewById(R.id.iv_addFav);
            mIv = itemView.findViewById(R.id.iv_fav_meal);
            mMealName = itemView.findViewById(R.id.tv_fav_meal_name);
            mDes = itemView.findViewById(R.id.tv_fav_meal_des);
            mRatingBar = itemView.findViewById(R.id.tv_fav_meal_row_rating);
            mRating = itemView.findViewById(R.id.tv_fav_meal_rating);
            mPrice = itemView.findViewById(R.id.tv_fav_meal_price);

            mButtonForAdd = itemView.findViewById(R.id.bt_add);
            mButtonAdded = itemView.findViewById(R.id.bt_added_cart);
            mImgButton = itemView.findViewById(R.id.iv_delete_item);
        }
    }
}
