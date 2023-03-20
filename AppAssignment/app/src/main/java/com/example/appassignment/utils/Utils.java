package com.example.appassignment.utils;

import com.example.appassignment.model.Cart;
import com.example.appassignment.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL ="http://192.168.176.1/product/";
    public static List<Cart> cartList;

    public static List<Cart> buyCartList = new ArrayList<>();
    public static User user_cur = new User();

    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
