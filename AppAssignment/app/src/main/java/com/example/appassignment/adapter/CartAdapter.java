package com.example.appassignment.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appassignment.Interface.ImageClickListener;
import com.example.appassignment.R;
import com.example.appassignment.model.Cart;
import com.example.appassignment.model.EventBus.TotalEvent;
import com.example.appassignment.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> listCart;

    public CartAdapter(Context context, List<Cart> listCart) {
        this.context = context;
        this.listCart = listCart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = listCart.get(position);
        holder.item_cart_name.setText(cart.getName());
        holder.item_quanlity_cart.setText(cart.getQuality() + " ");
        Glide.with(context).load(cart.getImage()).into(holder.item_cart_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_price_cart.setText(decimalFormat.format((cart.getPrice())));
        long price = cart.getQuality() + cart.getPrice();
        holder.item_price_total_money.setText(decimalFormat.format(price));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Utils.buyCartList.add(cart);
                    EventBus.getDefault().postSticky(new TotalEvent());
                }else{
                    for(int i = 0; i < Utils.buyCartList.size(); i++){
                        if(Utils.buyCartList.get(i).getId() == cart.getId()){
                            Utils.buyCartList.remove(i);
                            EventBus.getDefault().postSticky(new TotalEvent());
                        }
                    }
                }
            }
        });
        holder.setImageClickListener(new ImageClickListener() {
            @Override
            public void ImageClickListener(View view, int pos, int value) {
                Log.d("TAG", "onImageClick: " + pos + "..." + value);
                if(value == 1){
                    if(listCart.get(pos).getQuality() > 1){
                        int newQuantity = listCart.get(pos).getQuality()-1;
                        listCart.get(pos).setQuality(newQuantity);

                        holder.item_quanlity_cart.setText(listCart.get(pos).getQuality() + " ");
                        long price = listCart.get(pos).getQuality()+ listCart.get(pos).getPrice();
                        holder.item_price_total_money.setText(decimalFormat.format(price));
                        EventBus.getDefault().postSticky(new TotalEvent());
                    }else if(listCart.get(pos).getQuality() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Notification");
                        builder.setMessage("Do you want to delete product into cart");
                        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.cartList.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TotalEvent());
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();

                    }
                }else if (value == 2){
                    if(listCart.get(pos).getQuality() < 11){
                        int newQuantity = listCart.get(pos).getQuality()+1;
                        listCart.get(pos).setQuality(newQuantity);
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_cart_image, img_add, img_remove;
        TextView item_cart_name, item_price_cart, item_quanlity_cart, item_price_total_money;
        ImageClickListener imageClickListener;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart_image= itemView.findViewById(R.id.item_image_cart);
            item_cart_name = itemView.findViewById(R.id.item_name_cart);
            item_price_cart= itemView.findViewById(R.id.item_price_cart);
            item_quanlity_cart= itemView.findViewById(R.id.item_price_quanlity);
            item_price_total_money = itemView.findViewById(R.id.item_price_cart_total);
            img_add = itemView.findViewById(R.id.item_cart_add);
            img_remove = itemView.findViewById(R.id.item_cart_remove);
            checkBox = itemView.findViewById(R.id.item_cart_check);

            img_add.setOnClickListener(this);
            img_remove.setOnClickListener(this);
        }

        public void setImageClickListener(ImageClickListener imageClickListener) {
            this.imageClickListener = imageClickListener;
        }

        @Override
        public void onClick(View view) {
            if(view == img_remove){
                imageClickListener.ImageClickListener(view, getAdapterPosition(), 1);
            }else if(view == img_add){
                imageClickListener.ImageClickListener(view, getAdapterPosition(), 2);
            }
        }
    }
}
