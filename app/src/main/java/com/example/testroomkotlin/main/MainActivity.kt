package com.example.testroomkotlin.main

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testroomkotlin.adapter.MainViewHolder
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.RecyclerAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.Model
import com.example.testroomkotlin.info.InfoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var ad: RecyclerAdapter
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.instance(this)

        ad = RecyclerAdapter(object: MainViewHolder.Listener{
            override fun setOnClickListenerDelete(dataItem: Model) {
//                AsyncTask.execute {
//                    db.appDataBase().deleteWord(dataItem)
//                }
//                wwww()

                //Это переход из одного activity в дрпугое.
                //Создание перехода
                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                //Запуск переходи
                //putExtra нежен для передачи данных между активити
                intent.putExtra("viola", dataItem.title)
                startActivity(intent)
            }
        })

        addButton.setOnClickListener {
            val addItem = Model(0, edtext.text.toString())
            AsyncTask.execute {
                db.appDataBase().insertModel(addItem)
            }
            wwww()
        }

        deleteButton.setOnClickListener {
            AsyncTask.execute {
                db.appDataBase().deleteAllModel()
            }
            wwww()
        }

        wwww()
        rView.adapter = ad
    }

    fun wwww(){
        AsyncTask.execute {
            val l = db.appDataBase().getAllModel()
            runOnUiThread(Runnable {
                ad.setData(l)
            })
        }
    }
}
//on click in recycler, use adapter in interface