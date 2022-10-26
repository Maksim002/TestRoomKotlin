package com.example.testroomkotlin.ui.gallery

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
    var view: GalleryView? = null
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    fun onCreate() {
        fillingDatabase()
        launch {
            addList()
        }
    }

    private fun fillingDatabase(){
        firebaseDb.collection("db")
            .addSnapshotListener { value, error ->
                value?.documentChanges!!.forEach {
                    if (it.document.id != ""){
                        launch {
                            val modDb = db.appDataBaseFir().getAllModel()
                            val model = it.document.toObject(ModelGallery::class.java)
                            if (modDb.isNotEmpty()) {
                                if (modDb.firstOrNull { it.id == model.id} == null){
                                    db.appDataBaseFir().insertModel(ModelGallery(
                                        model.id, model.title, model.time, model.arrey, it.document.id
                                    ))
                                }
                            }else{
                                db.appDataBaseFir().insertModel(ModelGallery(
                                    model.id, model.title, model.time, model.arrey, it.document.id
                                ))
                            }
                        }
                    }
                }
            }
    }

    fun delete(position: ModelGallery){
        launch {
            db.appDataBaseFir().deleteWord(position)
            addList()
        }
    }

    private fun addList() {
        val modDb = db.appDataBaseFir().getAllModel()
        if (modDb.isNotEmpty()) {
            view!!.updateAdapter(modDb as ArrayList<ModelGallery>)
        }
    }
}