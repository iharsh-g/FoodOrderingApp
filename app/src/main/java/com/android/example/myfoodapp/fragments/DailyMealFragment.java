package com.android.example.myfoodapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.DailyMealAdapter;
import com.android.example.myfoodapp.models.DailyMeal;

import java.util.ArrayList;
import java.util.Random;

public class DailyMealFragment extends Fragment {

    private ArrayList<DailyMeal> mMealArrayList;
    private DailyMealAdapter mAdapter;
    private RecyclerView mRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_daily_meal, container, false);

        int[] discounts = new int[8];
        discounts[0] = 20;   discounts[1] = 12;    discounts[2] = 30;   discounts[3] = 25;
        discounts[4] = 30;   discounts[5] = 15;    discounts[6] = 32;   discounts[7] = 27;

        mMealArrayList = new ArrayList<>();

        mMealArrayList.add(new DailyMeal(R.drawable.breakfast, discounts[new Random().nextInt(discounts.length)], "Breakfast"));
        mMealArrayList.add(new DailyMeal(R.drawable.lunch, discounts[new Random().nextInt(discounts.length)], "Lunch"));
        mMealArrayList.add(new DailyMeal(R.drawable.dinner, discounts[new Random().nextInt(discounts.length)], "Dinner"));
        mMealArrayList.add(new DailyMeal(R.drawable.sweets, discounts[new Random().nextInt(discounts.length)], "Sweets"));
        mMealArrayList.add(new DailyMeal(R.drawable.coffe, discounts[new Random().nextInt(discounts.length)], "Coffee"));

        mAdapter = new DailyMealAdapter(getContext(), mMealArrayList);

        mRv = root.findViewById(R.id.daily_meal_rv);
        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }
}