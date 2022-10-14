package com.example.testroomkotlin.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelGallery(
    @PrimaryKey(autoGenerate = true)
   var id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String? = null
    )