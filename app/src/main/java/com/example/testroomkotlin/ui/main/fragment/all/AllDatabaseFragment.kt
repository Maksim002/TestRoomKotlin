package com.example.testroomkotlin.ui.main.fragment.all

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testroomkotlin.R
import com.example.testroomkotlin.ui.main.fragment.all.adapter.AllAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.ui.contentAll.AllContentActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*

class AllDatabaseFragment : Fragment(), AllDatabaseView {
    private lateinit var db: AppDataBase
    private lateinit var firebaseDb: FirebaseFirestore
    private lateinit var presenter: AllDatabaseRepository

    private lateinit var adapters: AllAdapter
    private val list: ArrayList<ModelGallery> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        db = AppDataBase.instance(requireContext())
        presenter = AllDatabaseRepository(db, firebaseDb, this)
        presenter.onCreate()
    }

    fun intends() = Intent(requireActivity(), AllContentActivity::class.java)
}