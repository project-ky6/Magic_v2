package com.example.magiccoffee_v2.DataLocal;

import android.content.Context;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DTO.Result;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataLocalManager {
    private static DataLocalManager instance;
    private static final String PREF_OBJECT_CART = "PREF_OBJECT_CART";
    private MySharedPreferences mySharedPreferences;
    private String Uid;

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static void setCart(Cart cart){
       ApiService.apiService.updateCart(cart).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
               String mess = response.body().getMessage();

                if (mess.equals("Thành công")){
                    Gson gson = new Gson();
                    String strJsonCart = gson.toJson(cart);
                    DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CART,strJsonCart);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public static void updateCart(CartItem cartItem, String Uid, String phoneNumber){
        ApiService.apiService.getCart(Uid).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                Cart temp = response.body();
                temp.setUid(Uid);
                temp.setPhoneNumber(phoneNumber);
                temp.addItem(cartItem);
                setCart(temp);
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }

    public static Cart getCart(){
        String strJsonCart = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_CART);
        Gson gson = new Gson();
        Cart cart = gson.fromJson(strJsonCart,Cart.class);
        return cart;
    }
    public static void deleteAllCart(){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CART,"");
    }
}
