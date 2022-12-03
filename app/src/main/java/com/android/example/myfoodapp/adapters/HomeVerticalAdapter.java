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
import com.android.example.myfoodapp.models.HomeVertical;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class HomeVerticalAdapter extends RecyclerView.Adapter<HomeVerticalAdapter.HomeVerticalVH> {

    private BottomSheetDialog bottomSheetDialog;
    private Context mContext;
    private ArrayList<HomeVertical> mList;
    private CartRoomDb mDatabase;

    public HomeVerticalAdapter(Context mContext, ArrayList<HomeVertical> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HomeVerticalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVerticalVH(LayoutInflater.from(mContext).inflate(R.layout.list_item_fragment_home_vertical, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerticalVH holder, int position) {
        HomeVertical curr = mList.get(position);
        mDatabase = CartRoomDb.getDatabase(mContext);
        SharedPreferences sharedPreferencesEmail = mContext.getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");

        String s = curr.getImage();
        int id = mContext.getResources().getIdentifier(s, "drawable", mContext.getPackageName());
        holder.mIv.setImageResource(id);

        holder.mName.setText(curr.getName());
        holder.mRating.setText(Float.toString(curr.getRating()));
        holder.mPrice.setText("" + curr.getPrice());
        holder.mTimings.setText(curr.getTiming());

        holder.mRate.setRating(curr.getRating());

        if(isInFav(email, curr.getMealId())) {
            holder.mFav.setImageResource(R.drawable.ic_filled_favorite);
        }
        else {
            holder.mFav.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_layout, null);

                AppCompatButton buttonAdded = sheetView.findViewById(R.id.bt_added_cart);
                ImageButton imagebutton = sheetView.findViewById(R.id.iv_delete_item);
                ImageView imageView = sheetView.findViewById(R.id.iv_bottom_sheet);
                AppCompatButton buttonForAdd = sheetView.findViewById(R.id.bt_add_cart);

                //Visibility of button of added to cart or not
                if(mDatabase.cartDao().isRowExist(curr.getMealId())) {
                    Log.e("HOME VERTICAL", "Row Exist both should be visible");
                    buttonAdded.setVisibility(View.VISIBLE);
                    imagebutton.setVisibility(View.VISIBLE);
                    buttonForAdd.setVisibility(View.GONE);
                }
                else {
                    Log.e("HOME VERTICAL", "Row not Exist 1 button visible");
                    buttonForAdd.setVisibility(View.VISIBLE);
                    buttonAdded.setVisibility(View.GONE);
                    imagebutton.setVisibility(View.GONE);
                }

                imageView.setImageResource(id);           //Set image

                //If User Add Item to the Cart
                buttonForAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CartData data = new CartData();
                        data.setFoodId(curr.getMealId());
                        data.setName(curr.getName());
                        data.setPrice(curr.getPrice());
                        data.setQuantity(1);
                        data.setFoodImage(s);
                        mDatabase.cartDao().insert(data);

                        Toast.makeText(mContext, "Item Added", Toast.LENGTH_SHORT).show();

                        bottomSheetDialog.cancel();
                    }
                });

                //If User Delete Item from Cart
                imagebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.cartDao().delete(curr.getMealId());
                        Toast.makeText(mContext, "Item Deleted", Toast.LENGTH_SHORT).show();

                        bottomSheetDialog.cancel();
                    }
                });

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
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
                    addFav(email, curr.getMealId(), curr.getName(), "",
                            curr.getImage(), curr.getPrice(), curr.getRating());
                    holder.mFav.setImageResource(R.drawable.ic_filled_favorite);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HomeVerticalVH extends RecyclerView.ViewHolder {

        private ImageView mIv, mFav;
        private TextView mName, mRating, mPrice, mTimings;
        private RatingBar mRate;

        public HomeVerticalVH(@NonNull View itemView) {
            super(itemView);

            mFav = itemView.findViewById(R.id.iv_addFav);
            mIv = itemView.findViewById(R.id.iv_vertical);
            mName = itemView.findViewById(R.id.tv_vertical_name);
            mRating = itemView.findViewById(R.id.tv__rating);
            mPrice = itemView.findViewById(R.id.tv_price);
            mTimings = itemView.findViewById(R.id.tv_timing);

            mRate = itemView.findViewById(R.id.row_rating_bar);
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
