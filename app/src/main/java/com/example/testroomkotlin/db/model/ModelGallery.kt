package com.example.testroomkotlin.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class ModelGallery(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "time")
    var time: String? = null,
    @ColumnInfo(name = "arrey")
    @TypeConverters(AddModeContent::class)
    var arrey: List<ContentModel>? = null
){
    //var value: FireFranValue = FireFranValue(false, 0)
    companion object Factory {
        fun create() :ContentModel = ContentModel()
    }
}