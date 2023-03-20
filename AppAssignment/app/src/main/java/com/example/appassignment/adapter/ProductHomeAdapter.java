package com.example.appassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appassignment.Interface.ItemClickListener;
import com.example.appassignment.R;
import com.example.appassignment.activity.DetailActivity;
import com.example.appassignment.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.MyViewHolder> {
    Context context;
    List<Product> array;

    public ProductHomeAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = array.get(position);
        holder.txtName.setText(product.getProductname());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.txtPrice.setText("Price" + decimalFormat.format(Double.parseDouble(product.getPrice())));
        Glide.with(context).load(product.getImage()).into(holder.imImage);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("detail", product);// put data sang trang details
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtPrice, txtName;
        ImageView imImage;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.item_price);
            txtName = itemView.findViewById(R.id.item_name);
            imImage = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}

