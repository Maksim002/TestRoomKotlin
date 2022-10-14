package com.example.testroomkotlin.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.GalleryAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.ui.addDB.AddActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryActivity: AppCompatActivity(), GalleryView{
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore

    private lateinit var adapters: GalleryAdapter
    private val list: ArrayList<ModelGallery> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initGallery()
    }

    private fun initGallery() {
        adapters = GalleryAdapter(object : GalleryAdapter.Listener{
            override fun setOnClickListener(position: Int, view: View) {
                val intent = Intent(this@GalleryActivity, AddActivity::class.java)
                startActivity(intent)
            }

            override fun setOnClickItem(position: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.appDataBaseFir().deleteWord(list[position])
                    list.removeAt(position)
                    addListAdapter()
                }
            }
        })
        list.add(ModelGallery(9379992, "Добавить"))
        addListAdapter()
        recyclerGallery.adapter = adapters
    }

    private fun addListAdapter(){
        if (::adapters.isInitialized){
            adapters.setData(list)
        }
    }

    override fun updateAdapter(modelGallery: ModelGallery?) {
        if (modelGallery != null){
            list.add(modelGallery)
        }
        addListAdapter()
    }

    override fun deleteModel(modelGallery: ModelGallery?) {
        list.remove(modelGallery)
        addListAdapter()
    }

    override fun addListMod(modelGallery: ModelGallery) {
        list.add(modelGallery)
        addListAdapter()
    }

    override fun onStart() {
        super.onStart()
        //База firebase
        firebaseDb = FirebaseFirestore.getInstance()
        //База mobile
        db = AppDataBase.instance(this)
        GalleryRepository(db, firebaseDb, this).onCreate()
    }
}