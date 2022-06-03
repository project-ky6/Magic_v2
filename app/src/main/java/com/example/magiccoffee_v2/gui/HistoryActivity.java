package com.example.magiccoffee_v2.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magiccoffee_v2.R;
import com.example.magiccoffee_v2.api.ApiService;
import com.example.magiccoffee_v2.dto.Cart;
import com.example.magiccoffee_v2.dto.User;
import com.example.magiccoffee_v2.gui.adapter.CartAdapter;
import com.example.magiccoffee_v2.gui.utils.RequestCode;
import com.example.magiccoffee_v2.gui.utils.Utils;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout llHistory, llCurrent;
    private RecyclerView rcvCurrentOrder, rcvOrderHistory;
    private List<Cart> cartsCurrent, cartsHistory;
    private CartAdapter cartCurrentAdapter, cartHistoryAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        user = (User) getIntent().getBundleExtra("Data").getSerializable("User");

        InitView();

        cartsCurrent = new ArrayList<Cart>();
        cartsHistory = new ArrayList<Cart>();
        cartCurrentAdapter = new CartAdapter(cartsCurrent, this,"History");
        cartHistoryAdapter = new CartAdapter(cartsHistory, this,"History");

        SetApdater(rcvCurrentOrder, cartCurrentAdapter);
        SetApdater(rcvOrderHistory, cartHistoryAdapter);

        InitEvent();
        Snapshot();
    }

    private void SetApdater(RecyclerView rcv, CartAdapter cartAdapter) {
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(cartAdapter);
    }

    private void InitEvent() {
    }

    private void InitView() {
        llHistory = findViewById(R.id.llHistory);
        llCurrent = findViewById(R.id.llCurrent);
        rcvCurrentOrder = findViewById(R.id.rcvCurrentOrder);
        rcvOrderHistory = findViewById(R.id.rcvOrderHistory);

        rcvCurrentOrder.setNestedScrollingEnabled(false);
        rcvOrderHistory.setNestedScrollingEnabled(false);

        llCurrent.setVisibility(View.GONE);
        llHistory.setVisibility(View.GONE);
    }
    private void Snapshot() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Carts")
            .whereEqualTo("Uid", user.getUid())
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        return;
                    }

                    cartsCurrent.clear();
                    cartsHistory.clear();
                    llCurrent.setVisibility(View.GONE);
                    llHistory.setVisibility(View.GONE);
                    CallAPI(cartsCurrent, cartCurrentAdapter,"current");
                    CallAPI(cartsHistory, cartHistoryAdapter,"history");
                }
            });

    }
    private void CallAPI(List<Cart> carts, CartAdapter cartAdapter, String type) {
        ApiService.apiService.getHistory(user.getUid(), type).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                carts.addAll(response.body());
                cartAdapter.notifyDataSetChanged();

                if(carts.size() > 0){
                    if(type.equals("current")){
                        llCurrent.setVisibility(View.VISIBLE);
                    }
                    else {
                        llHistory.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });

    }
}