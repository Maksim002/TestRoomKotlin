package com.example.testroomkotlin.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.GalleryAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.ui.addDB.AddActivity
import com.example.testroomkotlin.ui.content.AddContentActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity: AppCompatActivity(), GalleryView{
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore

    private lateinit var presenter: GalleryRepository

    private lateinit var adapters: GalleryAdapter
    private val list: ArrayList<ModelGallery> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initGallery()
    }

    private fun initGallery() {
        adapters = GalleryAdapter(object : GalleryAdapter.Listener{
            override fun setOnClickListener(position: Int, view: View, boolean: Boolean) {
                val intent: Intent
                if (!boolean){
                    intent = Intent(this@GalleryActivity, AddContentActivity::class.java)
                    intent.putExtra("value", false)
                }else{
                    intent = Intent(this@GalleryActivity, AddContentActivity::class.java)
                    intent.putExtra("value", true)
                    intent.putExtra("model", list[position])
                    intent.putExtra("op", presenter.open[list[position].id].toString())
                }
                startActivity(intent)
        }

            override fun setOnClickItem(position: Int) {
                presenter.delete(list[position].id?: 0)
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
        val index = list.indexOfFirst { it.id == modelGallery?.id }
        if (index != -1){
            list.removeAt(index)
            addListAdapter()
        }
    }

    override fun addListMod(modelGallery: ModelGallery) {
        val index = list.indexOfFirst { it.id == modelGallery.id}
        if (index != -1){
            list.removeAt(index)
        }
        list.add(modelGallery)
        addListAdapter()
    }

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