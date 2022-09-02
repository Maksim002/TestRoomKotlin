package com.example.testroomkotlin.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.AppDataBase
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        var db = AppDataBase.instance(this)

        val extras = intent.extras?.getString("viola")
        if (extras != null){
            textId.text = extras
        }
    }

}