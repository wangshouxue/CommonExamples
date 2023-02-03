package com.example.tileservicedemo

import android.content.Intent
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

/**
 * @Description: https://juejin.cn/post/7190663063631036473
 */
class MyTileService: TileService() {
    override fun onTileAdded() {
        super.onTileAdded()
//        当用户从编辑栏添加快捷开关到通知栏的快速设置中会调用。
    }

    override fun onClick() {
        super.onClick()
//        当用户点击快捷开关时调用。

//        点击快捷开关时、关闭通知栏并跳转到某个页面
        startActivityAndCollapse(Intent(applicationContext,Second::class.java))
    }
    override fun onStartListening() {
        super.onStartListening()
//        当用户打开通知栏的快速设置时调用。当快捷开关并没有从编辑栏拖到设置栏中不会调用。在TileAdded添加之后会调用一次。
        if (qsTile.state === Tile.STATE_ACTIVE) {
            qsTile.label = "关闭inactive"
            qsTile.icon = Icon.createWithResource(applicationContext, R.drawable.ic_launcher_background)
            qsTile.state = Tile.STATE_INACTIVE
        } else {
            qsTile.label = "打开active"
            qsTile.icon = Icon.createWithResource(applicationContext, R.drawable.ic_launcher_foreground)
            qsTile.state = Tile.STATE_ACTIVE
        }
        qsTile.updateTile()

    }

    override fun onStopListening() {
        super.onStopListening()
//        当用户打开通知栏的快速设置时调用。当快捷开关并没有从编辑栏拖到设置栏中不会调用。在TileRemoved移除之前会调用一次。
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        //当用户从通知栏的快速设置移除快捷开关时调用。
    }
}