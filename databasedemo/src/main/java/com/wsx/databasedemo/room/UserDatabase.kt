package com.wsx.databasedemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author:wangshouxue
 * @date:2022/4/22 上午11:26
 * @description:类作用
 */
@Database(entities = [UserBean::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var sInstance: UserDatabase? = null
        private const val DATA_BASE_NAME = "user.db"

        @JvmStatic
        fun getInstance(context: Context): UserDatabase? {
            if (sInstance == null) {
                synchronized(UserDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = createInstance(context)
                    }
                }
            }
            return sInstance
        }

        private fun createInstance(context: Context): UserDatabase {
            return Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, DATA_BASE_NAME).build()
        }
    }

//    @Database 表示继承自RoomDatabase的抽象类，entities指定Entities表的实现类列表，version指定了DB版本
//    必须提供获取DAO接口的抽象方法，比如上面定义的movieDao()，RoomDatabase将通过这个方法实例化DAO接口
//    RoomDatabase实例的内存开销较大，建议使用单例模式管理
//    编译的时候将生成_Impl实现类，此处将生成MovieDataBase_Impl.java文件

}