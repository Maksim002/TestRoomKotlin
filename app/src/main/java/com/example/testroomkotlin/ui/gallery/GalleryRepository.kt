package com.example.testroomkotlin.ui.gallery

import android.os.AsyncTask
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ModelGallery
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryRepository(
    private var db: AppDataBase,
    private var firebaseDb: FirebaseFirestore,
    var view: GalleryView
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    fun onCreate() {
        addList()
        firebaseToList()
    }

    private fun firebaseToList() {
     firebaseDb.collection("db")
         .addSnapshotListener { value, error ->
             value?.documentChanges!!.forEach {
                 if (it.type == DocumentChange.Type.ADDED){
                     sortBd(it.document.toObject(ModelGallery::class.java))
                 }
                 if (it.type == DocumentChange.Type.REMOVED){
                     launch {
                         val mod = it.document.toObject(ModelGallery::class.java)
                         db.appDataBaseFir().deleteWord(mod)
                         view.deleteModel(mod)
                     }
                 }
             }
         }
    }

    private fun sortBd(mod: ModelGallery) {
        launch {
            try {
                if (db.appDataBaseFir().getAllModel().firstOrNull {it.id != mod.id && it.title != mod.title } == null){
                    db.appDataBaseFir().insertModel(mod)
                    view.updateAdapter(mod)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun addList() {
        launch {
            val modDb = db.appDataBaseFir().getAllModel()
            if (modDb.isNotEmpty()) {
                for (i in modDb){
                   view.addListMod(i)
                }
            }
        }
    }
}