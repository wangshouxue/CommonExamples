package com.example.mynetdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        val tv=findViewById<TextView>(R.id.tv)
        val aboutUsApi: AboutUsApi? = RetrofitClient.getClient()?.create(
            AboutUsApi::class.java)
        val user: Call<AboutUsBean> ?=aboutUsApi?.usersInfo
        user?.enqueue(object : Callback<AboutUsBean> {
            override fun onResponse(call: Call<AboutUsBean>, response: Response<AboutUsBean>) {
                //主线程
                Thread.sleep(5000)

                var mytxt=response.body()?.data?.bottom_text?:"你好"
                tv.setText(mytxt)
            }

            override fun onFailure(call: Call<AboutUsBean>, t: Throwable) {

            }
        })
    }
}