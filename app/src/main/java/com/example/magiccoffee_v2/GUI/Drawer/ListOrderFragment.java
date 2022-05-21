package com.example.magiccoffee_v2.GUI.Drawer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.GUI.Adapter.CartAdapter;
import com.example.magiccoffee_v2.GUI.Static.RequestCode;
import com.example.magiccoffee_v2.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOrderFragment extends Fragment {

    private RecyclerView rcvListOrder;
    private List<Cart> carts;
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

        rcvListOrder = view.findViewById(R.id.rcvListOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvListOrder.setLayoutManager(linearLayoutManager);

        carts = new ArrayList<Cart>();
        cartAdapter = new CartAdapter(carts, getContext());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        rcvListOrder.setAdapter(cartAdapter);

        db.collection("Carts")
                .whereEqualTo("Status", "Order")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        carts.clear();
                        ApiService.apiService.getCartsByStatus(RequestCode.STATUS_CART_ORDER).enqueue(new Callback<List<Cart>>() {
                            @Override
                            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                                carts.addAll(response.body());
                                cartAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<List<Cart>> call, Throwable t) {

                            }
                        });
                    }
                });

        return view;
    }
}