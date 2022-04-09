package com.wsx.shortcutsdemo

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        方式1 在AndroidManifest.xml中

//        方式2
//        shortcut()

//        方式3
        initShortcuts()
        jumpShortcuts()
    }

    fun shortcut(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            val scManager: ShortcutManager = getSystemService(ShortcutManager::class.java)
            //跳转Second
            val secondIntent = Intent("secondback",android.net.Uri.EMPTY,
                this,Test::class.java) //此处的Test即为回退页面，快捷方式跳转的页面按下返回键后显示的页面
            val infoPublishPai: ShortcutInfo =ShortcutInfo.Builder(this, "two")
                .setShortLabel("go second")
                .setRank(1)
                .setIcon(android.graphics.drawable.Icon.createWithResource(this,R.drawable.icon_shortcut_pai))
                .setIntents(arrayOf<Intent>(secondIntent,Intent(this,Second::class.java).setAction("second")))
                .build()
            val list: MutableList<ShortcutInfo> = java.util.ArrayList<ShortcutInfo>()
            list.add(infoPublishPai)

            if (scManager != null) {
                scManager.setDynamicShortcuts(list)
            }
        }
    }
    /**
     * 7.1以上支持shortcuts
     */
    private fun initShortcuts() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            val scManager: ShortcutManager = getSystemService(ShortcutManager::class.java)
            //跳转Test
            val testIntent = Intent(Intent.ACTION_MAIN,android.net.Uri.EMPTY,
                this,MainActivity::class.java)//此处需为入口页面，否则快捷方式目标页面跳转不了
            testIntent.putExtra("shortcuts", "test")//自定义的规则，解析跳转时用到
            val infoForumPublish: ShortcutInfo = ShortcutInfo.Builder(this, "testid")
                .setShortLabel("测试一下")
                .setRank(0)
                .setIcon(android.graphics.drawable.Icon.createWithResource(this, R.drawable.icon_shortcut_post))
                .setIntents(arrayOf<Intent>(testIntent))
                .build()
//            action ,id 可以随便填
            //跳转Second
            val secondIntent = Intent("secondaction",android.net.Uri.EMPTY,
                this,MainActivity::class.java)
            secondIntent.putExtra("shortcuts", "second")
            val infoPublishPai: ShortcutInfo =ShortcutInfo.Builder(this, "secondid")
                .setShortLabel("go second")
                .setRank(1)
                .setIcon(android.graphics.drawable.Icon.createWithResource(this,R.drawable.icon_shortcut_pai))
                .setIntents(arrayOf<Intent>(secondIntent))
                .build()
            val list: MutableList<ShortcutInfo> = java.util.ArrayList<ShortcutInfo>()
            list.add(infoForumPublish)
            list.add(infoPublishPai)
            if (scManager != null) {
                scManager.setDynamicShortcuts(list)
            }
        }
    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        jumpShortcuts()
//    }

    private fun jumpShortcuts() {
        val shortcuts: String? =intent.getStringExtra("shortcuts")
        when(shortcuts){
            "test"->{
                startActivity(Intent(this,Test::class.java))
            }
            "second"->{
                startActivity(Intent(this,Second::class.java))
            }
        }
    }
}