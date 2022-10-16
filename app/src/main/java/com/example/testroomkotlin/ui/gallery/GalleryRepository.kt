package com.example.testroomkotlin.ui.gallery

import androidx.lifecycle.MutableLiveData
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GalleryRepository(
    private var db: AppDataBase,
    private var firebaseDb: FirebaseFirestore,
    var view: GalleryView? = null
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private var open : HashMap<Int,String> = hashMapOf()

    fun onCreate() {
        addList()
        firebaseToList()
    }

    private fun firebaseToList() {
     firebaseDb.collection("db")
         .addSnapshotListener { value, error ->
             value?.documentChanges!!.forEach {
                 if (it.type == DocumentChange.Type.REMOVED){
                     launch {
                         val mod = it.document.toObject(ModelGallery::class.java)
                         db.appDataBaseFir().deleteWord(mod)
                         view!!.deleteModel(mod)
                     }
                 }else{
                     launch {
                         if (it.document.id != ""){
                             sortBd(it.document.toObject(ModelGallery::class.java), it.document.id)
                         }
                     }
                 }
             }
         }
    }

    private fun sortBd(mod: ModelGallery, document: String) {
        try {
            if (db.appDataBaseFir().getAllModel().firstOrNull {it.id == mod.id} == null){
                db.appDataBaseFir().insertModel(mod)
                view!!.updateAdapter(mod)
            }
            open[mod.id!!.toInt()] = document
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    fun delete(position: Int){
        firebaseDb.collection("db")
            .document(open[position].toString())
            .delete()
    }

    private fun addList() {
        val open : HashMap<Int,ModelGallery> = hashMapOf()
        launch {
            val modDb = db.appDataBaseFir().getAllModel()
            if (modDb.isNotEmpty()) {
                for (i in modDb){
                   open[i.id!!] = i
                }
                open.map {view!!.addListMod(it.value)}
            }
        }
    }
}