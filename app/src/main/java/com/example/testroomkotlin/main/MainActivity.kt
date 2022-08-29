package com.example.testroomkotlin.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.AppDataBase

class MainActivity : AppCompatActivity() {
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.instance(this)

    }
}