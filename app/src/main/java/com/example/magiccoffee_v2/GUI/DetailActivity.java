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
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.DTO.Size;
import com.example.magiccoffee_v2.DTO.Temper;
import com.example.magiccoffee_v2.DTO.User;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private NumberFormat formatter;
    public User user;
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

        FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
        ApiService.apiService.getUser(userFb.getUid()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Không thể kết nối đến server.", Toast.LENGTH_SHORT).show();
            }
        });



        formatter = NumberFormat.getCurrencyInstance();
        initView();
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
        String moneyPrice = formatter.format(coffee.getPrice());
        txtPrice.setText(moneyPrice);
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
        totalPrice();
    }

    private void event() {
        imgBtnCart.setOnClickListener(view ->{
            // Chuyển activity từ detail -> cart
            Bundle bundle = new Bundle();
            bundle.putSerializable("User", user);

            Intent intent = new Intent(DetailActivity.this, CartActivity.class);
            intent.putExtra("Data", bundle);
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

            try{
                if(quantity > 0){
                    checkQuantity();
                    checkSize();
                    checkTemper();

                    String name = coffee.getName();
                    String image = coffee.getImageLink();
                    String cfId = coffee.getId();

                    CartItem cartItem = new CartItem(quantity, name, image, totalPrice, cfId, temper, size);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    DataLocalManager.updateCart(cartItem, user.getUid(),"096985509");
                    Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Số lượng phải lớn hơn 0 mới được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Có lỗi xảy ra "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

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
        String moneyTotal = formatter.format(totalPrice*quantity);
        txtTotalPrice.setText(moneyTotal);
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

    private void initView() {
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