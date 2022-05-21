package com.example.magiccoffee_v2.GUI.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.magiccoffee_v2.GUI.Drawer.ListOrderFragment;
import com.example.magiccoffee_v2.GUI.Drawer.OrderAdminFragment;
import com.example.magiccoffee_v2.GUI.Drawer.PayFragment;

public class OrderAdapter extends FragmentStatePagerAdapter {
    public OrderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListOrderFragment();
            case 1:
                return new PayFragment();
            default:
                return new ListOrderFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Đơn đặt";
                break;
            case 1:
                title = "Thanh toán";
                break;
            default:
                title = "List order";
                break;
        }
        return title;
    }
}
