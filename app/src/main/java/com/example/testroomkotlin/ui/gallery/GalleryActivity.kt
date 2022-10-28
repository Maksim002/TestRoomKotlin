package com.example.testroomkotlin.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.ui.main.fragment.all.adapter.AllAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.ui.contentAll.AllContentActivity
import com.example.testroomkotlin.ui.main.fragment.all.AllDatabaseRepository
import com.example.testroomkotlin.ui.main.fragment.all.AllDatabaseView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity: AppCompatActivity(), AllDatabaseView {
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore
    private lateinit var presenter: AllDatabaseRepository

    private lateinit var adapters: AllAdapter
    private val list: ArrayList<ModelGallery> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initGallery()
    }

    private fun initGallery() {
        adapters = AllAdapter(object : AllAdapter.Listener{
            override fun setOnClickListener(position: Int, view: View, boolean: Boolean) {
                val intent: Intent
                if (!boolean){
                    intent = intends()
                    intent.putExtra("value", false)
                }else{
                    intent = intends()
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
        recyclerMi.adapter = adapters
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
        presenter = AllDatabaseRepository(db, firebaseDb, this)
        presenter.onCreate()
    }

    fun intends() = Intent(this@GalleryActivity, AllContentActivity::class.java)
}