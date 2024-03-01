package com.example.myaidldemo1

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myaidldemo2.IMyAidlInterface

//参考：https://blog.csdn.net/afufufufu/article/details/131723540
class MainActivity : AppCompatActivity() {
//    private var demoServiceConnection: DemoServiceConnection?=null
//    服务端的IMyAidlInterface复制过来后需要重新build
    private var myAidlInterface: IMyAidlInterface?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt).setOnClickListener {
            val intent= Intent()
//            启动方式1
//            intent.component = ComponentName("com.example.myaidldemo2", "com.example.myaidldemo2.MyService")
//            启动方式2
            intent.setAction("com.example.myaidldemo2.MyService");
            intent.setPackage("com.example.myaidldemo2");//包名
            //绑定服务--需要在配置文件中增加queries，否则启动不了
            bindService(intent,object:ServiceConnection {
                override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
                    myAidlInterface= IMyAidlInterface.Stub.asInterface(iBinder)
                    if (myAidlInterface!=null) {
                        Toast.makeText(this@MainActivity,"服务已连接",Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    myAidlInterface=null
                    Toast.makeText(this@MainActivity,"服务未连接",Toast.LENGTH_SHORT).show()
                }

            },BIND_AUTO_CREATE)
        }
        findViewById<Button>(R.id.bt2).setOnClickListener {
            myAidlInterface?.sendMsg("我是1向2发送的消息")

        }

        val tv=findViewById<TextView>(R.id.tv)
        tv.setOnClickListener {
            tv.setText(myAidlInterface?.msg?:"未获取到")
        }
    }
//    private inner class DemoServiceConnection : ServiceConnection {
//        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
//            myAidlInterface= IMyAidlInterface.Stub.asInterface(iBinder)
//            if (myAidlInterface!=null) {
//                Toast.makeText(this@MainActivity,"服务已连接",Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//        override fun onServiceDisconnected(p0: ComponentName?) {
//            myAidlInterface=null
//            Toast.makeText(this@MainActivity,"服务未连接",Toast.LENGTH_SHORT).show()
//        }
//
//    }

}