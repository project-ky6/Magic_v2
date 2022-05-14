package com.example.magiccoffee_v2.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.DataLocal.DataLocalManager;
import com.example.magiccoffee_v2.GUI.Login.LoginActivity;
import com.example.magiccoffee_v2.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WellcomeActivity extends AppCompatActivity {
    private String filename = "Uid.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String Uid = readData();
                if(Uid != null){
                    if(!Uid.equals("")){
                        ApiService.apiService.getUser(Uid).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user1 = response.body();
                                //Nếu đã tồn tại đi vào trang chủ
                                Bundle bundle2 = new Bundle();
                                bundle2.putSerializable("User", user1);
                                Intent intent2 = new Intent(WellcomeActivity.this, MainActivity.class);
                                intent2.putExtra("Data", bundle2);
                                startActivity(intent2);
                                finish();
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Không thể kết nối đến server.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    startActivity(new Intent(WellcomeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 500);


    }
    private String readData()
    {
        String data = "";
        try
        {
            File file = getApplicationContext().getFileStreamPath(filename);
            if (file.exists()){
                FileInputStream fin = openFileInput(filename);
                int a;
                StringBuilder temp = new StringBuilder();
                while ((a = fin.read()) != -1)
                {
                    temp.append((char)a);
                }
                data = temp.toString();
                fin.close();
                return data;
            }
            else return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}