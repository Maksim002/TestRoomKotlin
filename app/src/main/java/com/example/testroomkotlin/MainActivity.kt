package com.example.testroomkotlin

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinroom.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var list: List<Model>? = null
    private var adapter: MainAdapter? = null
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.instance(this)
        adapter = MainAdapter()
        recyclerview.adapter = adapter
        add_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        AsyncTask.execute {
            list = db.appDataBase().getAllModel()
            runOnUiThread(Runnable {
                adapter?.setData(list!!)
            })
        }

    }

}