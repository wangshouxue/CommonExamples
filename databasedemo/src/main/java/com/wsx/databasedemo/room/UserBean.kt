package com.wsx.databasedemo.room

import androidx.room.Entity

/**
 * @author:wangshouxue
 * @date:2022/4/22 上午11:16
 * @description:类作用
 * Room是一个对象一张表，所以在要存储的bean上添加@Entity,框架就会自动建表
 * tableName属性可以为该表设置名字，如果不设置，则表名与类名相同。
 */
@Entity(tableName = "user",primaryKeys = ["id"])
data class UserBean(var id:Int,var username:String?,var sex:String?,var time:String)