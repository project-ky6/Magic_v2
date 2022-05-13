package com.example.magiccoffee_v2.GUI.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Category;
import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.GUI.Adapter.CoffeeAdapter;
import com.example.magiccoffee_v2.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private RecyclerView rcvMenu;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private TabLayout tabLayout;
    private RelativeLayout loading;


    List<Coffee> coffees;

    private CoffeeAdapter coffeeAdapter;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        tabLayout = view.findViewById(R.id.tablayout);

        rcvMenu = view.findViewById(R.id.rcvMenu);

        loading = view.findViewById(R.id.loading);

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        rcvMenu.setLayoutManager(gridLayoutManager);
        ApiService.apiService.getListCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> ltCategory = response.body();
                for (Category cate : ltCategory) {
                    tabLayout.addTab(tabLayout.newTab().setText(cate.getName()).setTag(cate.getType()));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

        ApiService.apiService.getListCoffee().enqueue(new Callback<List<Coffee>>() {
            @Override
            public void onResponse(Call<List<Coffee>> call, Response<List<Coffee>> response) {
                coffees = response.body();
                coffeeAdapter = new CoffeeAdapter(getContext(), coffees);
                rcvMenu.setAdapter(coffeeAdapter);
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Coffee>> call, Throwable t) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(coffees != null){
                    int index = 0;

                    for (int i = 0; i < coffees.size(); i++) {
                        if(coffees.get(i).getType().equals(tab.getTag().toString())){
                            index = i;
                            break;
                        }
                    }
                    gridLayoutManager.scrollToPositionWithOffset(index, 0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}