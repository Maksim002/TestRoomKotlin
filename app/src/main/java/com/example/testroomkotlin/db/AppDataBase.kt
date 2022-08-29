package com.example.testroomkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Model::class] ,version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDataBase() : MDao

    companion object{
        @Volatile
        private var instance: AppDataBase? = null

        fun instance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "Vio"
                ).build()
                Companion.instance = instance
                instance
            }

        }
    }
}