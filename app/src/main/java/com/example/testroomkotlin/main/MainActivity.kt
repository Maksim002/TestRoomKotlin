package com.example.testroomkotlin.main

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.RecyclerAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.Model
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_model.*

class MainActivity : AppCompatActivity() {
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.instance(this)

        val ad = RecyclerAdapter()

        addButton.setOnClickListener {
            val addItem = Model(0, edtext.text.toString())
            AsyncTask.execute {
                db.appDataBase().insertModel(addItem)
            }
            wwww(ad)
        }

        deleteButton.setOnClickListener {
            AsyncTask.execute {
                db.appDataBase().deleteAllModel()
            }
            wwww(ad)
        }

        wwww(ad)
        rView.adapter = ad
    }

    fun wwww(ad: RecyclerAdapter){
        AsyncTask.execute {
            val l = db.appDataBase().getAllModel()
            runOnUiThread(Runnable {
                ad.setData(l)
            })
        }
    }
}
//on click in recycler, use adapter in interface