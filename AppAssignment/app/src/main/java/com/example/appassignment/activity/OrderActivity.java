package com.example.appassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appassignment.MainActivity;
import com.example.appassignment.R;
import com.example.appassignment.retrofit.ApiProduct;
import com.example.appassignment.retrofit.RetrofitClient;
import com.example.appassignment.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView total_money, phone, email;
    EditText address;
    Button btn_checkout;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiProduct apiProduct;

    //bien
    long total_amount;
    int totalItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        viewById();
        countItem();
        viewControl();
    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i < Utils.buyCartList.size(); i++){
            totalItem = totalItem + Utils.buyCartList.get(i).getQuality();

        }
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        total_amount = getIntent().getLongExtra("totalItemCart", 0);
        total_money.setText(decimalFormat.format(total_amount));
        email.setText(Utils.user_cur.getEmail());
        phone.setText(Utils.user_cur.getPhone());
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saddress = address.getText().toString().trim();
                if(TextUtils.isEmpty(saddress)){
                    Toast.makeText(getApplicationContext(), "You don't put address", Toast.LENGTH_SHORT).show();
                }else{
                    String semail = Utils.user_cur.getEmail();
                    String sphone = Utils.user_cur.getPhone();
                    int sid = Utils.user_cur.getId();
                    Log.d("test", new Gson().toJson(Utils.buyCartList));
                    compositeDisposable.add(apiProduct.order(semail,sphone,String.valueOf(total_amount),sid,saddress,totalItem,new Gson().toJson(Utils.cartList))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                        Utils.buyCartList.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                            ));
                }
            }
        });
    }

    private void viewById() {
        apiProduct = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiProduct.class);
        toolbar = findViewById(R.id.toobar_order);
        email = findViewById(R.id.email_order);
        total_money = findViewById(R.id.total_money_order);
        phone = findViewById(R.id.phone_order);
        address = findViewById(R.id.address_order);
        btn_checkout = findViewById(R.id.btn_checkout);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}