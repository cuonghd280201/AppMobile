package com.example.appassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appassignment.R;
import com.example.appassignment.retrofit.ApiProduct;
import com.example.appassignment.retrofit.RetrofitClient;
import com.example.appassignment.utils.Utils;

import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout email, password, re_password, phone, username;

    Button btn_signup;
    ApiProduct apiProduct;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView txt_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        viewById();
        viewControl();
    }

    private void viewControl() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }

        });
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void signUp() {
        String semail = email.getEditText().getText().toString().trim();
        String spassword = password.getEditText().getText().toString().trim();
        String srepassword = re_password.getEditText().getText().toString().trim();
        String sphone = phone.getEditText().getText().toString().trim();
        String susername = username.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(semail)){
            Toast.makeText(getApplicationContext(),"You don't input email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(spassword)) {
            Toast.makeText(getApplicationContext(), "You don't input password", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(srepassword)) {
            Toast.makeText(getApplicationContext(), "You don't input re_password", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sphone)) {
            Toast.makeText(getApplicationContext(), "You don't input phone", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(susername)) {
            Toast.makeText(getApplicationContext(), "You don't input username", Toast.LENGTH_SHORT).show();
        }else{
            if(spassword.equals(srepassword)){
                compositeDisposable.add(apiProduct.user(semail,spassword,sphone,susername)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()){
                                        Utils.user_cur.setEmail(semail);
                                        Utils.user_cur.setPassword(spassword);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                ));
            }else{
                Toast.makeText(getApplicationContext(), "Confirm password failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewById() {
        apiProduct = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiProduct.class);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout)findViewById(R.id.password);
        re_password = (TextInputLayout)findViewById(R.id.re_password);
        username = (TextInputLayout)findViewById(R.id.username);
        phone = (TextInputLayout) findViewById(R.id.phone);
        btn_signup = findViewById(R.id.btn_signup);

        txt_login = findViewById(R.id.txt_login);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}