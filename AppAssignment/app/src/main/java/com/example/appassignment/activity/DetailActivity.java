package com.example.appassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appassignment.R;
import com.example.appassignment.model.Cart;
import com.example.appassignment.model.Product;
import com.example.appassignment.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import okhttp3.internal.Util;

public class DetailActivity extends AppCompatActivity {
    TextView named,priced,descriptiond;
    Button btnAdd;
    ImageView imgaged;
    Spinner spinner;
    Toolbar toolbar;
    Product product;
    NotificationBadge notificationBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewById();
        ActionToolBar();
        viewData();
        viewControl();

    }

    private void viewControl() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }

        });
    }
    private void addCart() {
        if(Utils.cartList.size() > 0){
            int quality = Integer.parseInt(spinner.getSelectedItem().toString());
           // check co trung product
            boolean tag = false;
            for(int i = 0; i< Utils.cartList.size(); i++){
                if(Utils.cartList.get(i).getId() == product.getId()){
                    Utils.cartList.get(i).setQuality(quality + Utils.cartList.get(i).getQuality());
                    long price = Long.parseLong(product.getPrice())  * Utils.cartList.get(i).getQuality();
                    Utils.cartList.get(i).setPrice(price);
                    tag = true;
                }
            }
            if(tag == false){
                long price = Long.parseLong(product.getPrice()) * quality;
                Cart cart = new Cart();
                cart.setPrice(price);
                cart.setQuality(quality);
                cart.setId(product.getId());
                cart.setName(product.getProductname());
                cart.setImage(product.getImage());
                Utils.cartList.add(cart);
            }
        }else{
            int quality = Integer.parseInt(spinner.getSelectedItem().toString());
            long price = Long.parseLong(product.getPrice()) * quality;
            Cart cart = new Cart();
            cart.setPrice(price);
            cart.setQuality(quality);
            cart.setId(product.getId());
            cart.setName(product.getProductname());
            cart.setImage(product.getImage());
            Utils.cartList.add(cart);
        }
        int totalItem = 0;
        for (int i = 0; i < Utils.cartList.size(); i++){
            totalItem = totalItem + Utils.cartList.get(i).getQuality();

        }
        notificationBadge.setText(String.valueOf(totalItem));

        //notificationBadge.setText(String.valueOf(Utils.cartList.size()));
    }
    private void viewData() {
        product= product = (Product) getIntent().getSerializableExtra("detail");
        named.setText(product.getProductname());
        descriptiond.setText(product.getDescription());
        Glide.with(getApplicationContext()).load(product.getImage()).into(imgaged);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        priced.setText("Price: " + decimalFormat.format(Double.parseDouble(product.getPrice())));
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(arrayAdapter);
    }

    private void viewById() {
        named = findViewById(R.id.name_d);
        priced = findViewById(R.id.price_d);
        descriptiond = findViewById(R.id.description_d);
        btnAdd = findViewById(R.id.button_add_detail);
        spinner = findViewById(R.id.spinner);
        imgaged = findViewById(R.id.image_d);
        toolbar = findViewById(R.id.toolbar_d);
        notificationBadge = findViewById(R.id.notification);
        //
        FrameLayout frameLayoutCart = findViewById(R.id.framework_cart);
        frameLayoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
        //Notification cart
        if(Utils.cartList != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.cartList.size(); i++){
                totalItem = totalItem + Utils.cartList.get(i).getQuality();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.cartList != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.cartList.size(); i++){
                totalItem = totalItem + Utils.cartList.get(i).getQuality();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }

    }
}