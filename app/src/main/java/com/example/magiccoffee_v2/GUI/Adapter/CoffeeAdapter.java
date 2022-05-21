package com.example.magiccoffee_v2.GUI.Adapter;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magiccoffee_v2.DTO.Coffee;
import com.example.magiccoffee_v2.DataLocal.ImageInternalStorage;
import com.example.magiccoffee_v2.GUI.BarcodeActivity;
import com.example.magiccoffee_v2.GUI.DetailActivity;
import com.example.magiccoffee_v2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeViewHolder>{
    public List<Coffee> lCoffee;
    private Context mContext;
    private boolean visibilityPrice = true;

    public CoffeeAdapter(Context mContext, List<Coffee> lCoffee, boolean visibilityPrice){
        this.lCoffee = lCoffee;
        this.mContext = mContext;
        this.visibilityPrice = visibilityPrice;
    }
    public CoffeeAdapter(Context mContext, List<Coffee> lCoffee){
        this.lCoffee = lCoffee;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public CoffeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee, parent, false);
        return new CoffeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeViewHolder holder, int position) {
        Coffee coffee = lCoffee.get(position);
        if(coffee == null) return;

        ImageInternalStorage.setImage(mContext, holder.imageView, coffee.getImageLink());

        holder.txtTitle.setText(coffee.getName());
        holder.txtPrice.setText(coffee.getPrice() + "");

        //Sản phẩm nổi bật
        if(!visibilityPrice){
            holder.txtPrice.setVisibility(View.GONE);
            holder.llLayout.setLayoutParams( new LinearLayout.LayoutParams(300,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        holder.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("Coffee", coffee);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Data", bundle);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(lCoffee != null)
            return lCoffee.size();
        return 0;
    }

    public class CoffeViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView txtTitle;
        public TextView txtPrice;
        public LinearLayout llLayout;;

        public CoffeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.img_item_coffe);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            llLayout = itemView.findViewById(R.id.layout_item_coffee);
        }
    }
}
