package com.example.testroomkotlin.db

import androidx.room.*
import com.example.testroomkotlin.db.model.AddModeContent
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.db.model.ModelGallery

@Dao
interface MDaoContent{

    @Query("SELECT * FROM addModeContent")
    fun getAllModel():List<AddModeContent>

    @Insert
    fun insert(addModeContent: AddModeContent)

    @Query("DELETE FROM addmodecontent ")
    fun deleteAllModel()

    @Insert
    fun insertModel(addModeContent: AddModeContent)

    @Delete
    fun deleteWord(addModeContent: AddModeContent)

    @Update
    fun update(addModeContent: AddModeContent)
}