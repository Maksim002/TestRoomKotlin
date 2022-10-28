package com.example.testroomkotlin.ui.main.fragment.mi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testroomkotlin.R
import com.example.testroomkotlin.ui.main.fragment.mi.adapter.MiAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.ui.contentAll.AllContentActivity
import com.example.testroomkotlin.ui.contentMi.MiContentActivity
import kotlinx.android.synthetic.main.activity_gallery.*

class MiDatabaseFragment : Fragment(), MiDatabaseView {
    private lateinit var db: AppDataBase
    private lateinit var presenter: MiDatabaseRepository

    private lateinit var adapters: MiAdapter
    private val list: ArrayList<Model> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_databasek, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGallery()
    }

    private fun initGallery() {
        adapters = MiAdapter(object : MiAdapter.Listener{
            override fun setOnClickListener(position: Int, view: View, boolean: Boolean) {
                val intent: Intent
                if (!boolean){
                    intent = intends()
                    intent.putExtra("value", false)
                }else{
                    intent = intends()
                    intent.putExtra("value", true)
                    intent.putExtra("model", list[position])
                }
                startActivity(intent)
            }

            override fun setOnClickItem(position: Int) {
                presenter.delete(list[position])
                list.removeAt(position)
                adapters.setData(list)
            }
        })
        list.add(Model(9379992, "Добавить"))
        addListAdapter()
        recyclerMi.adapter = adapters
    }

    private fun addListAdapter(){
        if (::adapters.isInitialized){
            adapters.setData(list)
        }
    }

    override fun updateAdapter(modelGallery: Model?) {
        if (modelGallery != null){
            list.add(modelGallery)
        }
        addListAdapter()
    }

    override fun deleteModel(modelGallery: Model?) {
        val index = list.indexOfFirst { it.id == modelGallery?.id }
        if (index != -1){
            list.removeAt(index)
            addListAdapter()
        }
    }

    override fun addListMod(modelGallery: Model) {
        val index = list.indexOfFirst { it.id == modelGallery.id}
        if (index != -1){
            list.removeAt(index)
        }
        list.add(modelGallery)
        addListAdapter()
    }

    override fun onStart() {
        super.onStart()
        //База mobile
        db = AppDataBase.instance(requireContext())
        presenter = MiDatabaseRepository(db, this)
        presenter.onCreate()
    }
    fun intends() = Intent(requireActivity(), MiContentActivity::class.java)
}