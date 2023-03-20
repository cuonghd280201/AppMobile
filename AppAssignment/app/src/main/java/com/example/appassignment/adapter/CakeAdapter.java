package com.example.appassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class CakeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Product> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    public CakeAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cake, parent, false);
            return new MyViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MyViewHolder){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                Product product = array.get(position);
                myViewHolder.cname.setText(product.getProductname());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                myViewHolder.cprice.setText("Price: " + decimalFormat.format(Double.parseDouble(product.getPrice())));
                myViewHolder.cdescription.setText(product.getDescription());
                Glide.with(context).load(product.getImage()).into(myViewHolder.imImage);
                myViewHolder.setItemClickListener(new ItemClickListener() {
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
            }else{
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
public class LoadingViewHolder extends RecyclerView.ViewHolder{
    ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }
}
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cname, cprice, cdescription;
        ImageView imImage;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cname = itemView.findViewById(R.id.cake_name);
            cprice = itemView.findViewById(R.id.cake_price);
            cdescription = itemView.findViewById(R.id.cake_description);
            imImage = itemView.findViewById(R.id.cake_img);
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
