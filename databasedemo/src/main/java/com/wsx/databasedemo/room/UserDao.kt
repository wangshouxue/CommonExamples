package com.wsx.databasedemo.room

import androidx.room.*

/**
 * @author:wangshouxue
 * @date:2022/4/22 上午11:20
 * @description:类作用
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user:UserBean)

    @Delete
    fun deleteUser(user:UserBean)

    @Update
    fun updateUser(user:UserBean)

    @Query("select * from user order by time desc")
    fun queryAllUser(user:UserBean)

    @Query("select * from user where username = :name and sex = :sex")
    fun queryUser(name:String,sex:String)

}