package com.example.testroomkotlin.ui.addDB

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val firebaseDb = FirebaseFirestore.getInstance()

        val db = AppDataBase.instance(this)

        add_button.setOnClickListener {
            val dataItem = ModelGallery(Random().nextInt(500) + 20, titleEditText.text.toString(),
                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

            AsyncTask.execute {
                db.appDataBaseFir().insertModel(dataItem)
                firebaseDb.collection("db")
                    .add(dataItem)
                finish()
            }
        }
    }
}
