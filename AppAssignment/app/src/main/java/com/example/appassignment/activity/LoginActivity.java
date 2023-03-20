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

import com.example.appassignment.MainActivity;
import com.example.appassignment.R;
import com.example.appassignment.retrofit.ApiProduct;
import com.example.appassignment.retrofit.RetrofitClient;
import com.example.appassignment.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView txt_signup;
    EditText email, password;
    Button btnLogin;
    ApiProduct apiProduct;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewById();
        viewControl();
    }

    private void viewById() {
        apiProduct = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiProduct.class);
        txt_signup = findViewById(R.id.txt_sigup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void viewControl() {
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                if(TextUtils.isEmpty(semail)){
                    Toast.makeText(getApplicationContext(), "You don't input email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(spassword)){
                    Toast.makeText(getApplicationContext(), "You don't input password", Toast.LENGTH_SHORT).show();
                }else{
                    compositeDisposable.add(apiProduct.login(semail,spassword)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            Utils.user_cur = userModel.getResult().get(0);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            )
                    );
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_cur.getEmail() != null && Utils.user_cur.getPassword() != null){
            email.setText(Utils.user_cur.getEmail());
            password.setText(Utils.user_cur.getPassword());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}