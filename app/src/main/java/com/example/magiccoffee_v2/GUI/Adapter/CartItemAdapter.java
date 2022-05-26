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
import com.example.magiccoffee_v2.GUI.my_interface.IClickItemCartListener;
import com.example.magiccoffee_v2.R;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>{

    private List<CartItem> cartItems;
    private Context mContext;
    private IClickItemCartListener listener;

    public CartItemAdapter(List<CartItem> cartItems, Context context,  IClickItemCartListener listener){
        this.cartItems = cartItems;
        this.mContext = context;
        this.listener = listener;
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

        holder.btnIncrease.setOnClickListener(view -> {
            int quantity = cartItem.getQuantity();
            quantity ++;
            cartItem.setQuantity(quantity);
            holder.txtQuantily.setText(quantity+"");
            listener.onClickItemCartPlus(position, quantity);

        });
        holder.btnDecrease.setOnClickListener(view -> {
            int quantity = cartItem.getQuantity();
            if(quantity >= 1){
                quantity --;
                cartItem.setQuantity(quantity);
                holder.txtQuantily.setText(quantity+"");
            }
            listener.onClickItemCartLess(position, quantity);
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
        public ImageView btnDecrease, btnIncrease;
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
