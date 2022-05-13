package com.example.magiccoffee_v2.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.R;

public class DetailActivity extends AppCompatActivity {

   private Button btnPlus, btnExcept;
   private ImageButton imgBtnBack, imgBtnCart;
   private TextView txtQuantity, txtName, txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.toolbar));
        }

        Bundle bundle = getIntent().getBundleExtra("Data");
        Coffee coffee = (Coffee) bundle.getSerializable("Coffee");

        findView();
        event();
        setData(coffee);
    }

    private void setData(Coffee coffee) {
        txtName.setText(coffee.getName());
        txtPrice.setText(coffee.getPrice()+"");
    }

    private void event() {
        imgBtnCart.setOnClickListener(view ->{
            // Chuyển activity từ detail -> cart
            Intent intent = new Intent(DetailActivity.this, CartActivity.class);
            startActivity(intent);
        });

        imgBtnBack.setOnClickListener(view->{
            finish();
        });

        btnPlus.setOnClickListener(view ->{
            int numQuan = Integer.parseInt(txtQuantity.getText().toString().trim());
            numQuan ++;
            txtQuantity.setText(numQuan+"");
        });

        btnExcept.setOnClickListener(view -> {
            int numQuan = Integer.parseInt(txtQuantity.getText().toString().trim());
            numQuan --;
            if(numQuan >= 0)
                txtQuantity.setText(numQuan+"");
        });
    }

    private void findView() {
        btnPlus = findViewById(R.id.btnPlus);
        btnExcept = findViewById(R.id.btnExcept);
        txtQuantity = findViewById(R.id.txtQuantity);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnCart = findViewById(R.id.imgBtnCart);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
    }
}