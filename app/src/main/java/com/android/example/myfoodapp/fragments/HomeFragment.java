package com.android.example.myfoodapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.myfoodapp.MainActivity;
import com.android.example.myfoodapp.NetworkUtils;
import com.android.example.myfoodapp.R;
import com.android.example.myfoodapp.adapters.HomeHorizontalAdapter;
import com.android.example.myfoodapp.adapters.HomeVerticalAdapter;
import com.android.example.myfoodapp.adapters.UpdateVerticalRecyclerView;
import com.android.example.myfoodapp.database.LoginRoomDb;
import com.android.example.myfoodapp.databinding.FragmentHomeBinding;
import com.android.example.myfoodapp.models.HomeHorizontal;
import com.android.example.myfoodapp.models.HomeVertical;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateVerticalRecyclerView {

    private ArrayList<HomeHorizontal> mList;
    private HomeHorizontalAdapter mAdapter;
    private FragmentHomeBinding mBinding;

    private HomeVerticalAdapter mAdapter2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = mBinding.getRoot();

        //If network is not available
        if(!NetworkUtils.isNetworkAvailable(getContext())){
            MakeInternetDialog();
            mBinding.tvUserName.setVisibility(View.GONE);
            mBinding.homeRvHorizontal.setVisibility(View.GONE);
            mBinding.homeRvVertical.setVisibility(View.GONE);
            mBinding.pbHome.setVisibility(View.VISIBLE);
        }
        else {
            mBinding.pbHome.setVisibility(View.GONE);
            mBinding.tvUserName.setVisibility(View.VISIBLE);
            mBinding.homeRvHorizontal.setVisibility(View.VISIBLE);
            mBinding.homeRvVertical.setVisibility(View.VISIBLE);
        }

        SharedPreferences sharedPreferencesEmail = getContext().getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferencesEmail.getString("email", "");

        //Setting User Name
        LoginRoomDb roomDb = LoginRoomDb.getDatabase(getContext());
        String s = roomDb.loginDao().getUserName(email);
        String[] temp = s.split(" ");

        mBinding.tvUserName.setText("Hello " + temp[0] + "!");

        mList = new ArrayList<>();

        mList.add(new HomeHorizontal(R.drawable.pizza, "Pizza"));
        mList.add(new HomeHorizontal(R.drawable.hamburger, "Hamburger"));
        mList.add(new HomeHorizontal(R.drawable.fried_potatoes, "Fries"));
        mList.add(new HomeHorizontal(R.drawable.ice_cream, "Ice Cream"));
        mList.add(new HomeHorizontal(R.drawable.sandwich, "Sandwich"));

        mAdapter = new HomeHorizontalAdapter(getContext(), mList, this);

        mBinding.homeRvHorizontal.setAdapter(mAdapter);
        mBinding.homeRvHorizontal.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mBinding.homeRvHorizontal.setNestedScrollingEnabled(false);

        /* ***************** Vertical Recycler View *************** */
        mBinding.homeRvVertical.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    private void MakeInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Internet Connection");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!NetworkUtils.isNetworkAvailable(getContext())){
                    MakeInternetDialog();
                }
                else {
                    mBinding.pbHome.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.pbHome.setVisibility(View.GONE);
                            mBinding.tvUserName.setVisibility(View.VISIBLE);
                            mBinding.homeRvHorizontal.setVisibility(View.VISIBLE);
                            mBinding.homeRvVertical.setVisibility(View.VISIBLE);
                        }
                    }, 2000);

                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void callBack(int position, ArrayList<HomeVertical> list) {
        mAdapter2 = new HomeVerticalAdapter(getContext(), list);
        mAdapter2.notifyDataSetChanged();
        mBinding.homeRvVertical.setAdapter(mAdapter2);
    }
}