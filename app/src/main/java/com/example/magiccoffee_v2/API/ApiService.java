package com.example.magiccoffee_v2.API;

import com.example.magiccoffee_v2.DTO.Branch;
import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.Category;
import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.DTO.Login;
import com.example.magiccoffee_v2.DTO.Member;
import com.example.magiccoffee_v2.DTO.Result;
import com.example.magiccoffee_v2.DTO.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder().create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://magic-coffee.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("branches")
    Call<List<Branch>> getListBranch();

    @GET("carts/setCart")
    Call<Cart> getCart(@Query("uid") String Uid);

    @GET("carts/statistical")
    Call<List<Cart>> getCartsByStatus(@Query("s") String status);

    @Headers({"Content-Type: application/json"})
    @POST("carts/update")
    Call<Result> updateCart(@Body Cart cart);

    @GET("users/{uid}")
    Call<User> getUser(@Path("uid") String Uid);

    @GET("categories")
    Call<List<Category>> getListCategory();

    @GET("coffees")
    Call<List<Coffee>> getListCoffee();

    //coffees?n=name
    @GET("coffees")
    Call<List<Coffee>> searchCoffees(@Query("n") String name);

    @Headers({"Content-Type: application/json"})
    @POST("users/create")
    Call<User> insertUser(@Body User user);

    @Headers({"Content-Type: application/json"})
    @POST("auth/login")
    Call<Member> login(@Body Login login);
}
