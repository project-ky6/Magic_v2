package com.example.magiccoffee_v2.DataLocal;

import android.content.Context;

import com.example.magiccoffee_v2.DTO.Cart;
import com.google.gson.Gson;

public class DataLocalManager {
    private static DataLocalManager instance;
    private static final String PREF_OBJECT_CART = "PREF_OBJECT_CART";
    private MySharedPreferences mySharedPreferences;

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
        Gson gson = new Gson();
        String strJsonCart = gson.toJson(cart);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CART,strJsonCart);
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
