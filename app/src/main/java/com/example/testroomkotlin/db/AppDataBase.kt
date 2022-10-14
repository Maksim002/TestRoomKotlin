package com.example.testroomkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.db.model.ModelGallery

@Database (entities = [Model::class, ModelGallery::class] ,version = 3)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDataBase() : MDao
    abstract fun appDataBaseFir() : MDaoGalliry

    companion object{
        @Volatile
        private var instance: AppDataBase? = null

        fun instance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "Room"
                ).build()
                Companion.instance = instance
                instance
            }
        }
    }
}