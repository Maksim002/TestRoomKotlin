package com.example.testroomkotlin.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
class AddModeContent (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @TypeConverters(AddModeContent::class)
    var arrey: List<ContentModel>? = null
)