package com.example.testroomkotlin.ui.main.fragment.mi

import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.db.model.ModelGallery
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MiDatabaseRepository(
    private var db: AppDataBase,
    var view: MiDatabaseView? = null
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    var open : HashMap<Int,String> = hashMapOf()

    fun onCreate() {
        addList()
    }

    private fun addList() {
        val open : HashMap<Int,Model> = hashMapOf()
        launch {
            val modDb = db.appDataBase().getAllModel()
            if (modDb.isNotEmpty()) {
                for (i in modDb){
                   open[i.id!!] = i
                }
                open.map {view!!.addListMod(it.value)}
            }
        }
    }

    fun delete(position: Model){
        launch {
            db.appDataBase().deleteWord(position)
        }
    }
}