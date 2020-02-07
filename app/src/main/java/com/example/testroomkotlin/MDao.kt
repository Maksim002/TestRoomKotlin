package com.example.testroomkotlin

import androidx.room.*

@Dao
interface MDao{

    @Query("SELECT * FROM model")
    fun getAllModel():List<Model>

    @Insert
    fun insert(word: Model)

    @Query("DELETE FROM model ")
    fun deleteAllModel()

    @Insert
    fun insertModel(model: Model)

    @Delete
    fun deleteWord(word: Model)

    @Update
    fun update(word:Model)
}