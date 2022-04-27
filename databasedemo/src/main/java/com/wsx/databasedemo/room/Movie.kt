package com.wsx.databasedemo.room

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author:wangshouxue
 * @date:2022/4/22 上午11:34
 * @description:类作用
 */
@Entity
class Movie() : LiveData<Movie>() {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "movie_name", defaultValue = "Harry Potter")
    lateinit var name: String

    @ColumnInfo(name = "actor_name", defaultValue = "Jack Daniel")
    lateinit var actor: String

    @ColumnInfo(name = "post_year", defaultValue = "1999")
    var year = 1999

    @ColumnInfo(name = "review_score", defaultValue = "8.0")
    var score = 8.0
}
