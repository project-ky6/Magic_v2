package com.example.magiccoffee_v2.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.DTO.Size;
import com.example.magiccoffee_v2.DTO.Temper;
import com.example.magiccoffee_v2.DataLocal.DataLocalManager;
import com.example.magiccoffee_v2.DataLocal.ImageInternalStorage;
import com.example.magiccoffee_v2.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

   private Button btnPlus, btnExcept, btnAddToCart;
   private ImageButton imgBtnBack, imgBtnCart;
   private ImageView imgAvt;
   private TextView txtQuantity, txtName, txtPrice, txtTotalPrice;
   private RadioButton btnNong, btnLanh, btnS, btnM, btnL;

   private Coffee coffee;
   private int totalPrice;
   private String size = "S";
   private String temper = "";
   private int quantity = 0;

    private ShareButton shareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.toolbar));
        }

        Bundle bundle = getIntent().getBundleExtra("Data");
        coffee = (Coffee) bundle.getSerializable("Coffee");

        findView();
        loadImage();
        shareFacebook();
        setData();
        event();
    }

    private void shareFacebook() {
        Bitmap image = ImageInternalStorage.loadImageBitmap(DetailActivity.this, coffee.getImageLink());
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#MagicCoffee")
                        .build())
                .build();
        shareButton.setShareContent(content);
    }

    private void loadImage() {
        ImageInternalStorage.setImage(DetailActivity.this, imgAvt, coffee.getImageLink());
    }

    private void setData() {
        txtName.setText(coffee.getName());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(coffee.getPrice());

        txtPrice.setText(moneyString);

        if(coffee.getTemper().size() <= 1){
            btnNong.setVisibility(View.GONE);
            btnLanh.setVisibility(View.GONE);
        }

        btnM.setVisibility(View.GONE);
        btnL.setVisibility(View.GONE);
        btnS.setVisibility(View.GONE);

        for (Size s: coffee.getSize()) {
            if(s.getNSize().equals("M")){
                btnM.setVisibility(View.VISIBLE);
            }
            if(s.getNSize().equals("S")){
                btnS.setVisibility(View.VISIBLE);
            }
            if(s.getNSize().equals("L")){
                btnL.setVisibility(View.VISIBLE);
            }
        }
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
            totalPrice();
        });

        btnExcept.setOnClickListener(view -> {
            int numQuan = Integer.parseInt(txtQuantity.getText().toString().trim());
            numQuan --;
            if(numQuan >= 0)
                txtQuantity.setText(numQuan+"");
            totalPrice();

        });

        btnL.setOnCheckedChangeListener((compoundButton, b) -> {
            totalPrice();
        });
        btnS.setOnCheckedChangeListener((compoundButton, b) -> {
            totalPrice();
        });
        btnM.setOnCheckedChangeListener((compoundButton, b) -> {
            totalPrice();
        });
        btnNong.setOnCheckedChangeListener((compoundButton, b) -> {
            totalPrice();
        });
        btnLanh.setOnCheckedChangeListener((compoundButton, b) -> {
            totalPrice();
        });
        btnAddToCart.setOnClickListener(view -> {

            checkQuantity();
            checkSize();
            checkTemper();

            String name = coffee.getName();
            String image = coffee.getImageLink();
            String cfId = coffee.getId();
            CartItem cartItem = new CartItem(quantity, name, image, totalPrice, cfId, temper, size);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            DataLocalManager.updateCart(cartItem, user.getUid(),"096985509");
        });
    }

    private void totalPrice() {

        checkQuantity();

        checkTemper();

        checkSize();


        totalPrice = coffee.getPrice();

        for (Size s: coffee.getSize()) {
            if(s.getNSize().equals(size))
                totalPrice += Float.parseFloat(s.getNotePrice());
        }
        for (Temper t: coffee.getTemper()) {
            if(t.getTpn().equals(temper))
                totalPrice += Float.parseFloat(t.getNotePrice());
        }
        txtTotalPrice.setText((totalPrice*quantity)+"");
    }

    private void checkQuantity() {
        quantity = Integer.parseInt(txtQuantity.getText().toString());
    }

    private void checkTemper() {
        if(coffee.getTemper().size() <= 1){
            temper = coffee.getTemper().get(0).getTpn();
        }
        else{
            if(btnLanh.isChecked()){
                temper = "Lạnh";
            }
            if(btnNong.isChecked()){
                temper = "Nóng";
            }
        }
    }

    private void checkSize() {
        if(btnM.isChecked())
            size= "M";
        else if(btnS.isChecked())
            size= "S";
        else if(btnL.isChecked())
            size = "L";
    }

    private void findView() {
        shareButton = findViewById(R.id.fb_share_button);
        btnNong = findViewById(R.id.btnNong);
        btnLanh = findViewById(R.id.btnLanh);
        btnPlus = findViewById(R.id.btnPlus);
        btnExcept = findViewById(R.id.btnExcept);
        txtQuantity = findViewById(R.id.txtQuantity);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnCart = findViewById(R.id.imgBtnCart);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnS = findViewById(R.id.btnS);
        btnM = findViewById(R.id.btnM);
        btnL = findViewById(R.id.btnL);
        txtTotalPrice= findViewById(R.id.txtTotalPrice);
        imgAvt = findViewById(R.id.imgAv);
    }
}