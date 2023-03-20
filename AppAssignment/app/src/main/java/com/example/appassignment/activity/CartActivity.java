package com.example.appassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appassignment.R;
import com.example.appassignment.adapter.CartAdapter;
import com.example.appassignment.model.Cart;
import com.example.appassignment.model.EventBus.TotalEvent;
import com.example.appassignment.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView noCart, totalMoney;
    Toolbar toolbar;
    RecyclerView recyclerView;

    Button btnBuy;
    CartAdapter cartAdapter;

    long totalItemCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        viewById();
        viewControl();
        if(Utils.buyCartList != null){
            Utils.buyCartList.clear();
        }
        totalAmount();
    }

    private void totalAmount() {
        totalItemCart = 0;
        for(int i = 0; i < Utils.buyCartList.size(); i++){
            totalItemCart = totalItemCart + ((Utils.buyCartList.get(i).getPrice() * Utils.buyCartList.get(i).getQuality()));
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        totalMoney.setText(decimalFormat.format(totalItemCart));
    }

    private void viewControl() {
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
       recyclerView.setHasFixedSize(true);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
       if(Utils.cartList.size() == 0){
           noCart.setVisibility(View.VISIBLE);
       }else{
           cartAdapter = new CartAdapter(getApplicationContext(),Utils.cartList);
           recyclerView.setAdapter(cartAdapter);
       }
       btnBuy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
               intent.putExtra("totalItemCart", totalItemCart);
               Utils.cartList.clear();

               startActivity(intent);
           }
       });
    }

    private void viewById() {
        noCart = findViewById(R.id.no_cart);
        toolbar = findViewById(R.id.toobar_cart);
        recyclerView = findViewById(R.id.recycleview_cart);
        totalMoney = findViewById(R.id.total_money);
        btnBuy = findViewById(R.id.btnBuy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventMoney(TotalEvent event){
        if(event != null){
            totalAmount();
        }
    }
}