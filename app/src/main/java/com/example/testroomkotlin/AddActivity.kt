package com.example.testroomkotlin

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val db = AppDataBase.instance(this)

        add_button.setOnClickListener {

            val dataItem = Model(0, titleEditText.text.toString())

            AsyncTask.execute {
                db.appDataBase().insertModel(dataItem)
                finish()
            }
        }
    }
}