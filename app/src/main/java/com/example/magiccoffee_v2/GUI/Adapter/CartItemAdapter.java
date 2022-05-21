package com.example.magiccoffee_v2.GUI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magiccoffee_v2.DTO.CartItem;
import com.example.magiccoffee_v2.DataLocal.ImageInternalStorage;
import com.example.magiccoffee_v2.R;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>{

    private List<CartItem> cartItems;
    private Context mContext;

    public CartItemAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem  = cartItems.get(position);
        if(cartItem == null) return;

        ImageInternalStorage.setImage(mContext, holder.imgCoffee, cartItem.getImage());

        holder.txtNameCf.setText(cartItem.getName());
        holder.txtQuantily.setText(cartItem.getQuantity()+"");
        holder.txtPrice.setText(cartItem.getPrice()+"");
        holder.txtSize.setText("Size "+cartItem.getSize());
        holder.txtTemper.setText(cartItem.getTemper());


        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.setQuantity(cartItem.getQuantity()+1);
            }
        });
        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.setQuantity(cartItem.getQuantity()-1);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (cartItems == null)
            return 0;
        return cartItems.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgCoffee;
        public TextView txtNameCf, txtSize, txtTemper;
        public TextView txtPrice;
        public TextView txtQuantily;
        public Button btnDecrease, btnIncrease;
        public LinearLayout llLayout;;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtTemper = itemView.findViewById(R.id.txtTemper);
            imgCoffee = itemView.findViewById(R.id.imgCoffee);
            txtNameCf = itemView.findViewById(R.id.txtNameCf);
            txtPrice = itemView.findViewById(R.id.txtPriceCf);
            txtQuantily = itemView.findViewById(R.id.txtQuantity);
            btnDecrease = itemView.findViewById(R.id.btnExcept);
            btnIncrease = itemView.findViewById(R.id.btnPlus);
        }
    }
}
