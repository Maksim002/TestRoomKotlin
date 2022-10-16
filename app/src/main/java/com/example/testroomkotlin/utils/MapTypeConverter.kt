package com.example.testroomkotlin.utils

import androidx.room.TypeConverter
import com.example.testroomkotlin.db.model.ContentModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object MapTypeConverter {
    private val gson = Gson()

    private inline fun <reified T> fromJson(json: String?, cls: Class<T>?): List<T> {
        val list: MutableList<T> = ArrayList()
        try {
            val gson = Gson()
            val arry = JsonParser().parse(json).asJsonArray
            for (jsonElement in arry) {
                list.add(gson.fromJson(jsonElement, cls))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }


    private inline fun <reified T> Any.toJson(): String =
        gson.toJson(this, object : TypeToken<List<T>>() {}.type)


    @TypeConverter
    @JvmStatic
    fun fromLong(value: List<ContentModel>): String = value.toJson<ContentModel>()
    @TypeConverter
    @JvmStatic
    fun toLong(value: String): List<ContentModel> =  fromJson(value, ContentModel::class.java)
}