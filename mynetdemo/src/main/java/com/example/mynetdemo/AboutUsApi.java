package com.example.mynetdemo;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @Description: 类作用描述
 */
public interface AboutUsApi {
    @GET("init/about")
    Call<AboutUsBean> getUsersInfo();
}
