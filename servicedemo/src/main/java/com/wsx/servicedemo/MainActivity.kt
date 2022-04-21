package com.wsx.servicedemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv=findViewById<TextView>(R.id.tv)
        tv.setOnClickListener {
            Log.i("===","Click")
//            val intent=Intent(this,BindService::class.java)
//            bindService(intent, serviceConn,BIND_AUTO_CREATE)
//            startService(intent)
            install()
        }
    }
    val serviceConn=object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder:BindService.MyBind= service as BindService.MyBind
            val bindService=binder.getBindService()
            tv.text=bindService.serviceMedthod()

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
    fun install(){
        try {
            val path ="${Environment.getExternalStorageDirectory().absolutePath}/Android/data/${packageName}/dzp.apk"
//            非自己app创建的文件不可访问
//            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/browser/dzp.apk"
            var apkFile=File(path)
            val intent = Intent(Intent.ACTION_VIEW)
            //兼容7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val provider: String = getPackageName() + ".fileprovider"
                val contentUri = FileProvider.getUriForFile(this, provider, apkFile)
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (getPackageManager().queryIntentActivities(intent, 0).size > 0) {
                // 或者startActivityForResult(intent, 10086);然后在相应页面接收回调处理安装
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}