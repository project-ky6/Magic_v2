package com.example.magiccoffee_v2.GUI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magiccoffee_v2.DTO.Cart;
import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DataLocal.ImageInternalStorage;
import com.example.magiccoffee_v2.GUI.Drawer.DetailCartActivity;
import com.example.magiccoffee_v2.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private List<Cart> carts;
    private Context mContext;

    public CartAdapter(List<Cart> carts, Context context) {
        this.carts = carts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart  = carts.get(position);
        if(cart == null) return;


//        holder.imgOrder.setImageBitmap(ImageInternalStorage.loadImageBitmap(mContext,"americano.png"));
        holder.txtSDT.setText(cart.getPhoneNumber());
        holder.txtTimeReceive.setText(cart.getPrice());
        holder.txtPrice.setText(cart.getPrice());

        holder.lLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailCartActivity.class);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        if (carts == null)
            return 0;
        return carts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgOrder;
        public TextView txtSDT, txtTimeReceive, txtPrice;
        public LinearLayout lLayout;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSDT = itemView.findViewById(R.id.txtSDT);
            txtTimeReceive = itemView.findViewById(R.id.txtTimeReceive);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgOrder = itemView.findViewById(R.id.imgOrder);
            lLayout = itemView.findViewById(R.id.lLayout);
        }
    }
}
