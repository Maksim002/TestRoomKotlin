package com.example.testroomkotlin.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.GalleryAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity: AppCompatActivity(), GalleryView{
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore

    private lateinit var adapters: GalleryAdapter
    private val list: ArrayList<ModelGallery> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        //База firebase
        firebaseDb = FirebaseFirestore.getInstance()
        //База mobile
        db = AppDataBase.instance(this)
        GalleryRepository(db, firebaseDb, this).onCreate()

        initGallery()
    }

    private fun initGallery() {
        adapters = GalleryAdapter(object : GalleryAdapter.Listener{
            override fun setOnClickListener(position: Int, view: View) {

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
}