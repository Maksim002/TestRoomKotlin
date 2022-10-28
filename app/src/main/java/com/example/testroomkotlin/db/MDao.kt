package com.example.testroomkotlin.db

import androidx.room.*
import com.example.testroomkotlin.db.model.Model

@Dao
interface MDao{

    @Query("SELECT * FROM model")
    fun getAllModel():List<Model>

    @Query("DELETE FROM model ")
    fun deleteAllModel()

    @Insert
    fun insertModel(model: Model)

    @Delete
    fun deleteWord(word: Model)

    @Update
    fun update(word: Model)
}