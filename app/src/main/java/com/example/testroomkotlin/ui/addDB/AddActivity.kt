package com.example.testroomkotlin.ui.addDB

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.Model
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*


class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val firebaseDb = FirebaseFirestore.getInstance()

        val db = AppDataBase.instance(this)

        add_button.setOnClickListener {
            val dataItem = Model(Random().nextInt(500) + 20, titleEditText.text.toString())

            AsyncTask.execute {
                db.appDataBase().insertModel(dataItem)
                firebaseDb.collection("db")
                    .add(dataItem)
                finish()
            }
        }
    }
}
