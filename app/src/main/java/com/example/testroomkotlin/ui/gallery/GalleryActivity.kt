package com.example.testroomkotlin.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.GalleryAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.ui.content.AddContentActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity(), GalleryView {
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore

    private lateinit var presenter: GalleryRepository

    private lateinit var adapters: GalleryAdapter
    private var list: ArrayList<ModelGallery> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initGallery()
    }

    private fun initGallery() {
        adapters = GalleryAdapter(object : GalleryAdapter.Listener {
            override fun setOnClickListener(position: Int, view: View, boolean: Boolean) {
                val intent: Intent
                if (!boolean){
                    intent = getIntents()
                    intent.putExtra("value", -1)
                }else{
                    intent = getIntents()
                    intent.putExtra("value", position)
                }
                startActivity(intent)
            }

            override fun setOnClickItem(position: Int) {
                presenter.delete(list[position])
            }
        })
        list.add(ModelGallery(9379992, "Добавить"))
        adapters.setData(list)
        recyclerGallery.adapter = adapters
    }


    override fun updateAdapter(modelGallery: ArrayList<ModelGallery>) {
        list = modelGallery
        list.add(ModelGallery(9379992, "Добавить"))
        adapters.setData(list)
    }

    fun getIntents() = Intent(this, AddContentActivity::class.java)

    override fun onStart() {
        super.onStart()
        //База firebase
        firebaseDb = FirebaseFirestore.getInstance()
        //База mobile
        db = AppDataBase.instance(this)
        presenter = GalleryRepository(db, firebaseDb, this)
        presenter.onCreate()
    }
}