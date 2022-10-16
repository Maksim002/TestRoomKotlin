package com.example.testroomkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testroomkotlin.db.model.AddModeContent
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.utils.MapTypeConverter

@Database (entities = [Model::class, ModelGallery::class, AddModeContent::class] ,version = 4)
@TypeConverters(
    MapTypeConverter::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDataBase() : MDao
    abstract fun appDataBaseFir() : MDaoGalliry
    abstract fun appDataBaseCon() : MDaoContent

    companion object{
        @Volatile
        private var instance: AppDataBase? = null

        fun instance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "RoomTests"
                ).build()
                Companion.instance = instance
                instance
            }
        }
    }
}