package com.example.magiccoffee_v2.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DTO.Result;
import com.example.magiccoffee_v2.DataLocal.DataLocalManager;
import com.example.magiccoffee_v2.GUI.Adapter.CartItemAdapter;
import com.example.magiccoffee_v2.GUI.Static.RequestCode;
import com.example.magiccoffee_v2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CartItem> cartItems;
    private RelativeLayout loading;
    private ImageButton btnBack;
    private Button btnThanhToan;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.magiccoffee_v2.R.layout.activity_cart);

        recyclerView = findViewById(R.id.rcvItemCart);
        loading = findViewById(R.id.loading);
        btnBack = findViewById(R.id.btnBack);
        btnThanhToan = findViewById(R.id.btnThanhToan);

        loading.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartItems = new ArrayList<CartItem>();
        CartItemAdapter cartAdapter = new CartItemAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ApiService.apiService.getCart(user.getUid()).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                cart = response.body();
                if(cart != null)
                {
                    if(cart.getItems().size() > 0){
                        for(CartItem cartItem: cart.getItems()){
                            cartItems.add(cartItem);
                            cartAdapter.notifyDataSetChanged();
                        }
                        loading.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                cart = DataLocalManager.getCart();
                if(cart != null)
                {
                    if(cart.getItems().size() > 0){
                        for(CartItem cartItem: cart.getItems()){
                            cartItems.add(cartItem);
                            cartAdapter.notifyDataSetChanged();
                        }
                        loading.setVisibility(View.GONE);
                    }

                }
            }
        });
        btnThanhToan.setOnClickListener(view -> {
            if(cart != null){
                if(cart.getItems().size() > 0)
                {
                    cart.setStatus(RequestCode.STATUS_CART_ORDER);
                    ApiService.apiService.updateCart(cart).enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            String mess = response.body().getMessage();

                            if (mess.equals("Thành công")){
                                DataLocalManager.deleteAllCart();
                                Toast.makeText(getApplicationContext(),  "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                            }
                            else  Toast.makeText(getApplicationContext(),  "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                        }
                    });
                }
            }
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}