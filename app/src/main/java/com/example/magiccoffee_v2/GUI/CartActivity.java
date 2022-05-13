package com.example.magiccoffee_v2.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.GUI.Adapter.CartAdapter;
import com.example.magiccoffee_v2.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CartItem> cartItems;
    private RelativeLayout loading;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.magiccoffee_v2.R.layout.activity_cart);

        recyclerView = findViewById(R.id.rcvItemCart);
        loading = findViewById(R.id.loading);
        btnBack = findViewById(R.id.btnBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartItems = new ArrayList<CartItem>();

        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));
        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));
        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));
        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));
        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));
        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));
        cartItems.add(new CartItem(3, "HUng", "sass",10202, "ưqdhwqod", "Nóng"));

        CartAdapter cartAdapter = new CartAdapter(cartItems, this);

        recyclerView.setAdapter(cartAdapter);

        loading.setVisibility(View.GONE);

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}