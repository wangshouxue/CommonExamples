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
        initShortcuts()
        jumpShortcuts()
    }

    /**
     * 7.1以上支持shortcuts
     */
    private fun initShortcuts() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            val scManager: ShortcutManager = getSystemService(ShortcutManager::class.java)
            //跳转Test
            val testIntent = Intent("test",android.net.Uri.EMPTY,
                this,MainActivity::class.java)
            testIntent.putExtra("shortcuts", "test")
            val infoForumPublish: ShortcutInfo = ShortcutInfo.Builder(this, "test")
                .setShortLabel("测试一下")
                .setIcon(android.graphics.drawable.Icon.createWithResource(this, R.drawable.icon_shortcut_post))
                .setIntents(arrayOf<Intent>(testIntent))
                .build()
            //跳转Second
            val secondIntent = Intent("second",android.net.Uri.EMPTY,
                this,MainActivity::class.java)
            secondIntent.putExtra("shortcuts", "second")
            val infoPublishPai: ShortcutInfo =ShortcutInfo.Builder(this, "second")
                .setShortLabel("go second")
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