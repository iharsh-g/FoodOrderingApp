package com.android.example.myfoodapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.FavouritesAdapter;
import com.android.example.myfoodapp.database.FavouritesData;
import com.android.example.myfoodapp.database.FavouritesRoomDb;
import com.android.example.myfoodapp.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment implements FavouritesAdapter.OnItemListener {

    private ArrayList<FavouritesData> mList;
    private FavouritesRoomDb favouritesRoomDb;
    private FragmentFavouriteBinding mBinding;
    private String email = "";
    private FavouritesAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentFavouriteBinding.inflate(inflater, container, false);

        favouritesRoomDb = FavouritesRoomDb.getDatabase(getContext());

        SharedPreferences sharedPreferencesEmail = getContext().getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        email = sharedPreferencesEmail.getString("email", "");

        mList = new ArrayList<>();
        mList = (ArrayList<FavouritesData>) favouritesRoomDb.favouritesDao().getAll(email);

        mAdapter = new FavouritesAdapter(mList, getContext(), this);

        mBinding.rvFav.setAdapter(mAdapter);
        mBinding.rvFav.setLayoutManager(new LinearLayoutManager(getContext()));

        onClick();

        return mBinding.getRoot();
    }

    @Override
    public void onClick() {
        Log.e("FF", "" + mAdapter.getItemCount());
        if(mAdapter.getItemCount() == 0) {
            mBinding.ivEmptyFav.setVisibility(View.VISIBLE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
            mBinding.tvEmpty1.setVisibility(View.VISIBLE);

            mBinding.rvFav.setVisibility(View.GONE);
        }
        else {
            mBinding.ivEmptyFav.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
            mBinding.tvEmpty1.setVisibility(View.GONE);

            mBinding.rvFav.setVisibility(View.VISIBLE);
        }
    }
}