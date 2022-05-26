package com.example.magiccoffee_v2.GUI.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.OrderInfo;
import com.example.magiccoffee_v2.DTO.Result;
import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.GUI.MainActivity;
import com.example.magiccoffee_v2.GUI.Static.RequestCode;
import com.example.magiccoffee_v2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    private TextInputEditText txtHoTen, txtPhoneNumber, txtAddress, txtEmail;
    private User user;
    private String actionCode;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.toolbar));
        }
        findView();
        checkAction();

        txtHoTen.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtPhoneNumber.setText(user.getPhoneNumber());

        btnSave.setOnClickListener(view -> {
            setDataForUser();
            saveData();
        });
    }

    private void saveData() {
        if(!actionCode.equals(RequestCode.UPDATE_INFO+"")){
            ApiService.apiService.insertUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    System.out.println(response);
                    System.out.println("Cập nhật thành công");

                    Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("Cập nhật thất bại");
                }
            });
        }
        else{
            //Update Info
            ApiService.apiService.updateUser(user).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    String result = response.body().getMessage();
                    if(result.equals("Thành công")){
                        Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Lỗi kết nối"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void setDataForUser() {
        user.setEmail(txtEmail.getText().toString());
        user.setPhoneNumber(txtPhoneNumber.getText().toString());
        user.setName(txtHoTen.getText().toString());
        user.setPhoneNumber(txtPhoneNumber.getText().toString());

        OrderInfo orderInfo = new OrderInfo(txtAddress.getText().toString(),"Active" ,txtPhoneNumber.getText().toString());

        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
        orderInfos.add(orderInfo);
        user.setOrderInfos(orderInfos);
    }
    private void checkAction() {
        actionCode = getIntent().getAction();

        if(actionCode.equals(RequestCode.UPDATE_INFO+"")){
            Bundle bundle = getIntent().getBundleExtra("Data");
            user = (User) bundle.getSerializable("User");
            txtAddress.setText(user.getOrderInfos().get(0).getAdress());
        }
        else {
            Bundle bundle = getIntent().getBundleExtra("Data");
            user = (User) bundle.getSerializable("User");
        }
    }
    private void findView() {
        btnSave = findViewById(R.id.btnSave);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtAddress = findViewById(R.id.txtDiaChi);
        txtPhoneNumber = findViewById(R.id.txtSoDienThoai);
        txtEmail = findViewById(R.id.txtEmail);
    }
}