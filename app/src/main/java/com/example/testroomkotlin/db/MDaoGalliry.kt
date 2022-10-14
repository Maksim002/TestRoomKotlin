package com.example.testroomkotlin.db

import androidx.room.*
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.db.model.ModelGallery

@Dao
interface MDaoGalliry{

    @Query("SELECT * FROM modelgallery")
    fun getAllModel():List<ModelGallery>

    @Insert
    fun insert(modelgallery: ModelGallery)

    @Query("DELETE FROM modelgallery ")
    fun deleteAllModel()

    @Insert
    fun insertModel(modelgallery: ModelGallery)

    @Delete
    fun deleteWord(modelgallery: ModelGallery)

    @Update
    fun update(modelgallery: ModelGallery)
}