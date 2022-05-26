package com.example.magiccoffee_v2.GUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DTO.Result;
import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.DataLocal.DataLocalManager;
import com.example.magiccoffee_v2.GUI.Adapter.CartAdapter;
import com.example.magiccoffee_v2.GUI.Adapter.CartItemAdapter;
import com.example.magiccoffee_v2.GUI.Static.RequestCode;
import com.example.magiccoffee_v2.GUI.my_interface.IClickItemCartListener;
import com.example.magiccoffee_v2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CartItem> cartItems;
    private RelativeLayout loading, emptyCart;
    private ImageButton btnBack;
    private Button btnThanhToan;
    private TextView txtTotalPrice,txtIntoMoney, txtIntoMoney2;
    private Cart cart;
    private NumberFormat formatter;
    private CartItemAdapter cartAdapter;
    private EditText edtGhiChu;
    private Calendar c;
    private TimePickerDialog timePickerDialog = null;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.magiccoffee_v2.R.layout.activity_cart);
        c = Calendar.getInstance();
        InitView();
        TimePickerListener();
        Bundle bundle = getIntent().getBundleExtra("Data");
        user = (User) bundle.getSerializable("User");


        formatter = NumberFormat.getCurrencyInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartItems = new ArrayList<CartItem>();
        cartAdapter = new CartItemAdapter(cartItems, this, new IClickItemCartListener() {
            @Override
            public void onClickItemCartPlus(int position, int quantity) {
                cart.getItems().get(position).setQuantity(quantity);
                cart.updateTotalPrice();
                //có vấn đề
                DataLocalManager.updateCart(cart.getItems().get(position), user.getUid(),user.getPhoneNumber());
                SetData(cart);
            }

            @Override
            public void onClickItemCartLess(int position, int quantity) {
                cart.getItems().get(position).setQuantity(quantity);
                cart.updateTotalPrice();
                //có vấn đề
                DataLocalManager.updateCart(cart.getItems().get(position), user.getUid(),user.getPhoneNumber());
                SetData(cart);
            }
        });
        recyclerView.setAdapter(cartAdapter);

        GetAPI();
        InitEvent();
    }

    private void TimePickerListener() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay < 8){
                    Toast.makeText(getApplicationContext(),  "Chưa đến thời gian làm việc", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (hourOfDay > 21){
                    Toast.makeText(getApplicationContext(),  "Đã hết giờ làm việc", Toast.LENGTH_SHORT).show();
                    return;
                }
                String strDateOrder = hourOfDay+":"+minute;
                String strDateNow = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);

                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date dateOrder = null;
                Date dateNow = null;
                try {
                    dateOrder = format.parse(strDateOrder);
                    dateNow = format.parse(strDateNow);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long difference = dateOrder.getTime() - dateNow.getTime();

                int myMinute = (int) (difference/(60 * 1000) % 60);

                if(myMinute < 15){
                    Toast.makeText(getApplicationContext(), "Thời gian nhận phải lớn hơi 15 phút kể từ bây giờ", Toast.LENGTH_SHORT).show();
                }
                else{
                    AddCart();
                }
            }
        };

        timePickerDialog = new TimePickerDialog(this,
                timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Thời gian nhận");
    }

    private void AddCart() {
        if(cart != null){
            if(cart.getItems().size() > 0)
            {
                cart.setStatus(RequestCode.STATUS_CART_ORDER);
                cart.setNote(edtGhiChu.getText().toString());
                ApiService.apiService.updateCart(cart).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        String mess = response.body().getMessage();
                        if (mess.equals("Thành công")){
                            DataLocalManager.deleteAllCart();
                            Toast.makeText(getApplicationContext(),  "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                            emptyCart.setVisibility(View.VISIBLE);
                        }
                        else  Toast.makeText(getApplicationContext(),  "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),  "Lỗi kết nối xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void GetAPI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            ApiService.apiService.getCart(user.getUid()).enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    cart = response.body();
                    SetCart(cart);
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {
                    cart = DataLocalManager.getCart();
                    SetCart(cart);
                }
            });
        }
    }

    private void InitEvent() {
        btnThanhToan.setOnClickListener(view -> {
            timePickerDialog.show();

        });

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void SetCart(Cart cart) {
        if(cart != null)
        {
            if(cart.getItems().size() > 0){
                for(CartItem cartItem: cart.getItems()){
                    cartItems.add(cartItem);
                    cartAdapter.notifyDataSetChanged();
                }
                SetData(cart);
                loading.setVisibility(View.GONE);
            }
            else {
                //Load empty cart
                loading.setVisibility(View.GONE);
                emptyCart.setVisibility(View.VISIBLE);
            }
        }
        else {
            loading.setVisibility(View.GONE);
            emptyCart.setVisibility(View.VISIBLE);
        }
    }

    private void InitView() {
        recyclerView = findViewById(R.id.rcvItemCart);
        loading = findViewById(R.id.loading);
        btnBack = findViewById(R.id.btnBack);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtIntoMoney = findViewById(R.id.txtIntoMoney);
        txtIntoMoney2 = findViewById(R.id.txtIntoMoney2);
        emptyCart = findViewById(R.id.emptyCart);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        loading.setVisibility(View.VISIBLE);
    }

    private void SetData(Cart cart){
        if(!cart.getPrice().equals("")){
            String moneyPrice = formatter.format(Integer.parseInt(cart.getPrice()));
            txtTotalPrice.setText(moneyPrice);
            txtIntoMoney.setText(moneyPrice);
            txtIntoMoney2.setText(moneyPrice);
        }
    }
}