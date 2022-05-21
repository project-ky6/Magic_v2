package com.example.magiccoffee_v2.GUI.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.GUI.Adapter.CoffeeAdapter;
import com.example.magiccoffee_v2.GUI.BarcodeActivity;
import com.example.magiccoffee_v2.GUI.Login.LoginActivity;
import com.example.magiccoffee_v2.GUI.SearchActivity;
import com.example.magiccoffee_v2.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Toolbar toolbar;
    private Button btnLoginSigin;
    private ImageButton imgBtnSearch, imgBtnBarcode;

    private RecyclerView recyclerView;
    private CardView cardView;

    CoffeeAdapter coffeeAdapter;
    LinearLayoutManager HorizontalLayout ;
    private User user;

    public HomeFragment(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getActivity().getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        findView(view);
        setAdapter();
        event();
        if(user.getUid() != null){
            cardView.setVisibility(View.GONE);
        }



        return view;
    }

    private void setAdapter() {
        ArrayList<Coffee> coffees = getListCoffee();
        coffeeAdapter = new CoffeeAdapter(getContext(),coffees, false);
        HorizontalLayout = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(coffeeAdapter);
    }

    private void findView(View view) {
        imgBtnBarcode = view.findViewById(R.id.imgBtnBarcode);
        toolbar = view.findViewById(R.id.toolBar);
        btnLoginSigin = view.findViewById(R.id.btnLoginSigin);
        recyclerView = view.findViewById(R.id.rcvTop);
        cardView = view.findViewById(R.id.crdViewLogin);
        imgBtnSearch = view.findViewById(R.id.imgBtnSearch);
    }

    private ArrayList<Coffee> getListCoffee() {
        ArrayList<Coffee> al = new ArrayList<>();

        al.add(new Coffee("Phin Sữa đá", "dqw", 29000 ));
        al.add(new Coffee("Phin Đen đá", "dqwd", 29000 ));
        al.add(new Coffee("Bạc Xỉu", "dw", 29000 ));
        al.add(new Coffee("Phin Sữa Nóng", "dwqd", 29000 ));
        al.add(new Coffee("Phin Đen Nóng", "dqwd", 29000 ));

        return al;
    }

    private void event(){
        imgBtnSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        btnLoginSigin.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        imgBtnBarcode.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), BarcodeActivity.class);
            startActivity(intent);
        });

    }
}