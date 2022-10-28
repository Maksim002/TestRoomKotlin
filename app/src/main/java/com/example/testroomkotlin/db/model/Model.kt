package com.example.testroomkotlin.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity
data class Model(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "time")
    var time: String? = null,
    @ColumnInfo(name = "arrey")
    @TypeConverters(AddModeContent::class)
    var arrey: List<ContentModel>? = null
): Serializable