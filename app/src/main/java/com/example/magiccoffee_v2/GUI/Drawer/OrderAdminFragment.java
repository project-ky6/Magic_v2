package com.example.magiccoffee_v2.GUI.Drawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magiccoffee_v2.GUI.Adapter.OrderAdapter;
import com.example.magiccoffee_v2.R;
import com.google.android.material.tabs.TabLayout;


public class OrderAdminFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_admin, container, false);


        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.view_pager);


        OrderAdapter orderAdapter = new OrderAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(orderAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}