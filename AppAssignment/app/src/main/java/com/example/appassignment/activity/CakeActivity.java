package com.example.appassignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.appassignment.R;
import com.example.appassignment.adapter.CakeAdapter;
import com.example.appassignment.adapter.ProductHomeAdapter;
import com.example.appassignment.model.Product;
import com.example.appassignment.retrofit.ApiProduct;
import com.example.appassignment.retrofit.RetrofitClient;
import com.example.appassignment.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CakeActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiProduct apiProduct;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int type;
    CakeAdapter cakeAdapter;
    List<Product> listProduct;

    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);
        apiProduct = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiProduct.class);
        type = getIntent().getIntExtra("type", 1);
        ViewById();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == listProduct.size()-1){
                        isLoading = true;
                        loadMore();
                    }

                }
            }


        });
    }
    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listProduct.add(null);
                cakeAdapter.notifyItemChanged(listProduct.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listProduct.remove(listProduct.size()-1);
                cakeAdapter.notifyItemRemoved(listProduct.size());
                page = page + 1;
                cakeAdapter.notifyDataSetChanged();
                isLoading = false;

            }
        }, 2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiProduct.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                if(cakeAdapter == null){
                                    listProduct = productModel.getResult();
                                    cakeAdapter= new CakeAdapter(getApplicationContext(),listProduct);
                                    recyclerView.setAdapter(cakeAdapter);
                                }else{
                                    int location = listProduct.size()-1;
                                    int numberadd = productModel.getResult().size();
                                    for(int i =0; i < numberadd; i++){
                                        listProduct.add(productModel.getResult().get(i));
                                    }
                                    cakeAdapter.notifyItemRangeInserted(location, numberadd);
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), "Not data", Toast.LENGTH_LONG).show();
                                isLoading = true;

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi voi server", Toast.LENGTH_LONG).show();
                        }
                ));
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

    private void ViewById() {
        toolbar = findViewById(R.id.toolbar_cake);
        recyclerView = findViewById(R.id.recycleview_cake);
       // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        listProduct = new ArrayList<>();
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}