package com.example.appassignment.retrofit;

import com.example.appassignment.model.ProductModel;
import com.example.appassignment.model.TypeProductModel;
import com.example.appassignment.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProduct {
    @GET("gettypeproduct.php")
    Observable<TypeProductModel> getTypeProduct();

    @GET("getproduct.php")
    Observable<ProductModel> getProduct();

    @POST("details.php")
    @FormUrlEncoded
    Observable<ProductModel> getDetails(
            @Field("page") int page,
            @Field("type") int type
    );

    @POST("user.php")
    @FormUrlEncoded
    Observable<UserModel> user(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username,
            @Field("phone") String phone
    );

    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password

    );

    @POST("order.php")
    @FormUrlEncoded
    Observable<UserModel> order(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("total") String total,
            @Field("iduser") int id,
            @Field("address") String address,
            @Field("quality") int quality,
            @Field("orderdetail") String orderdetail



    );


}
