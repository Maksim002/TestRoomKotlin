package com.example.testroomkotlin.ui.main

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.adapter.MainAdapter
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.ui.addDB.AddActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var list: ArrayList<Model> = arrayListOf()
    private lateinit var adapter: MainAdapter
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore
    private var open : ArrayList<DocumentChange> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(object : MainAdapter.Listener {
            override fun setOnClickListener(position: Int, view: View) {
                deleteItem(view, position)
            }
        })

        //База firebase
        firebaseDb = FirebaseFirestore.getInstance()
        //База mobile
        db = AppDataBase.instance(this)

        firebaseDb.collection("user")
            .addSnapshotListener { value, error ->
                value?.documentChanges!!.forEach {
                    if (it.type == DocumentChange.Type.REMOVED) {
                        removeBd("position", it.document.toObject(Model::class.java))
                    }
                    if (it.type != DocumentChange.Type.REMOVED) {
                        val listMode = open.firstOrNull { m -> m.document.id == it.document.id }
                        if (listMode == null) {
                            open.add(it)
                        }
                    }
                }
                if (value.documentChanges.isEmpty()) {
                    removeBd("all")
                } else {
                    for (i: DocumentChange in value.documentChanges) {
                        if (i.type == DocumentChange.Type.ADDED) {
                            setRepetition(i.document.toObject(Model::class.java))
                        }
                    }
                    AsyncTask.execute {
                        val modDb = db.appDataBase().getAllModel()
                        if (modDb.isNotEmpty()) {
                            for (i in modDb)
                                setRepetition(i)
                        }
                    }
                }
            }

        recyclerview.adapter = adapter


        add_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    // Сортировка данныз предотврощение дуближа
    private fun setRepetition(model: Model) {
        if (list.firstOrNull { it.title == model.title } == null) {
            list.add(model)
            adapter.setData(list)
        }
    }

    //В зависимости ото логики удаляю список
    private fun removeBd(name: String, model: Model? = null) {
        if (name == "position") {
            val removeIndex = list.indexOfFirst { it.title == model?.title }
            if (removeIndex != -1) {
                val removeModel = list[removeIndex]
                AsyncTask.execute {
                    db.appDataBase().deleteWord(removeModel)
                }
                list.removeAt(removeIndex)
                adapter.setData(list)
            }
        } else {
            AsyncTask.execute {
                db.appDataBase().deleteAllModel()
            }
            adapter.setData(listOf())
        }
    }

    //Функция отвечает за удаление длементов из писка
    //fun anim
    private fun deleteItem(rowView: View, position: Int) {
        val anim = AnimationUtils.loadAnimation(
            this,
            android.R.anim.slide_out_right
        )
        anim.duration = 300
        rowView.startAnimation(anim)
        Handler().postDelayed(Runnable {
            try {
                val s =  open[position].document.toObject(Model::class.java)
                val sd = list.indexOfFirst{it.title == s.title }
                if (sd != -1){
                    AsyncTask.execute {
                        db.appDataBase().deleteWord(s)
                    }
                    firebaseDb.collection("user")
                        .document(open[sd].document.id)
                        .delete()
                    open.remove(open[sd])
                }
            }catch (e: Exception){
                e.toString()
            }
            list.removeAt(position)
            adapter.setData(list)
        }, anim.duration)
    }
}