package com.example.appassignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appassignment.activity.CakeActivity;
import com.example.appassignment.activity.CartActivity;
import com.example.appassignment.activity.ChatActivity;
import com.example.appassignment.activity.LoginActivity;
import com.example.appassignment.activity.MessageActivity;
import com.example.appassignment.adapter.ProductHomeAdapter;
import com.example.appassignment.adapter.TypeProductAdapter;
import com.example.appassignment.model.Product;
import com.example.appassignment.model.ProductModel;
import com.example.appassignment.model.TypeProduct;
import com.example.appassignment.retrofit.ApiProduct;
import com.example.appassignment.retrofit.RetrofitClient;
import com.example.appassignment.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMainHome;
    NavigationView navigationView;
    ListView listViewMainHome;

    TypeProductAdapter typeProductAdapter;

    List<TypeProduct> arrayTypeP;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ApiProduct apiProduct;
    DrawerLayout drawerLayout;
    //list product home
    List<Product> listProduct;
    ProductHomeAdapter productHomeAdapter;

    NotificationBadge notificationBadge;
    FrameLayout frameLayout;
    ImageView imgChat;
    int totalItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiProduct = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiProduct.class);

        ViewByID();
        ActionBar();


        if(isConnected(this)){
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            ActionViewFlipper();
            getTypeProduct();
            getProduct();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(), "No Connected", Toast.LENGTH_SHORT).show();

        }
    }

    private void getEventClick() {
        listViewMainHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(home);
                        break;
                    case 1:
                        Intent cake = new Intent(getApplicationContext(), CakeActivity.class);
                        cake.putExtra("type", 1);
                        startActivity(cake);
                        break;
                    case 2:
                        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                }
            }
        });
    }

    private void getProduct() {
        compositeDisposable.add(apiProduct.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                listProduct = productModel.getResult();
                                productHomeAdapter = new ProductHomeAdapter(getApplicationContext(),listProduct);
                                recyclerViewMainHome.setAdapter(productHomeAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi voi server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getTypeProduct() {
       compositeDisposable.add(apiProduct.getTypeProduct()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       typeProductModel -> {
                           if(typeProductModel.isSuccess()){
                               arrayTypeP = typeProductModel.getResult();
                               typeProductAdapter = new TypeProductAdapter(getApplicationContext(),arrayTypeP);
                               listViewMainHome.setAdapter(typeProductAdapter);
                           }
                       },
                       throwable -> {
                           Toast.makeText(getApplicationContext(), "Khong ket noi voi server", Toast.LENGTH_LONG).show();
                       }
               ));

    }

    private void ActionViewFlipper() {
        List<String> listAdvertisement = new ArrayList<>();
        listAdvertisement.add("https://i.pinimg.com/736x/b9/fd/ca/b9fdca83ca8c97b553c74b0408f6aa96.jpg");
        listAdvertisement.add("https://handletheheat.com/wp-content/uploads/2015/03/Best-Birthday-Cake-with-milk-chocolate-buttercream-SQUARE.jpg");
        listAdvertisement.add("https://w7.pngwing.com/pngs/454/622/png-transparent-chocolate-cake-birthday-cake-tart-bakery-cheesecake-fruit-cake-cream-frutti-di-bosco-food.png");
        for(int i = 0; i<listAdvertisement.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(listAdvertisement.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }
    private void ViewByID() {
        toolbar = findViewById(R.id.toobarmainhome);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewMainHome = findViewById(R.id.recycle_view_home);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewMainHome.setLayoutManager(layoutManager);
        recyclerViewMainHome.setHasFixedSize(true);
        listViewMainHome = findViewById(R.id.listviewmainhome);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        notificationBadge = findViewById(R.id.notification);
        imgChat = findViewById(R.id.home_chat);
        frameLayout = findViewById(R.id.framework_cart);
        //list

        arrayTypeP = new ArrayList<>();
        listProduct = new ArrayList<>();
        //adapter
        if(Utils.cartList == null){
            Utils.cartList = new ArrayList<>();
        }else{
            totalItem = 0;
            for(int i = 0; i < Utils.cartList.size(); i++){
                totalItem = totalItem + Utils.cartList.get(i).getQuality();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
            }
        });

    }
    //tro lai trang home
    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for(int i = 0; i < Utils.cartList.size(); i++){
            totalItem = totalItem + Utils.cartList.get(i).getQuality();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }

    }
}