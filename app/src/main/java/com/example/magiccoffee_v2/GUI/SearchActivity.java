package com.example.magiccoffee_v2.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.GUI.Adapter.CoffeeAdapter;
import com.example.magiccoffee_v2.R;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private SearchView search_vew_pr;
    private RelativeLayout loading;
    private TagGroup mTagGroup;
    private RecyclerView rcvMenu;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private CoffeeAdapter coffeeAdapter;
    private List<Coffee> coffees = new ArrayList<Coffee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_vew_pr = findViewById(R.id.search_vew_pr);
        loading = findViewById(R.id.loading);
        rcvMenu = findViewById(R.id.rcvMenu);
        loading.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rcvMenu.setLayoutManager(gridLayoutManager);

        coffeeAdapter = new CoffeeAdapter(SearchActivity.this, coffees);

        search_vew_pr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    loading.setVisibility(View.VISIBLE);
                    Search(s);
                }
                catch (Exception ex){

                }
                return true;
            }
        });
        rcvMenu.setAdapter(coffeeAdapter);
        mTagGroup = findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Đá xay", "Bạc xỉu",
                "Phin di","Sealy","Christopher"});
        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                search_vew_pr.setQuery(tag,false);
                hideSoftKeyboard(search_vew_pr);
            }
        });
    }

    private void Search(String s) {
        ApiService.apiService.searchCoffees(s).enqueue(new Callback<List<Coffee>>() {
            @Override
            public void onResponse(Call<List<Coffee>> call, Response<List<Coffee>> response) {
                coffees.clear();
                List<Coffee> lt = response.body();
                if(lt != null){
                    if(lt.size() > 0){
                        coffees.addAll(lt);
                        coffeeAdapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                    }
                    else{
                        loading.setVisibility(View.GONE);
                    }
                }
                else {
                    loading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Coffee>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Kết nỗi server xảy ra dán đoạn", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm
                =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}