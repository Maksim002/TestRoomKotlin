package com.example.testroomkotlin.ui.content

import android.content.Intent
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ContentModel
import com.example.testroomkotlin.db.model.ModelGallery
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddContentPresenter(
    private var db: AppDataBase,
    private var firebaseDb: FirebaseFirestore,
    var view: AddContentView? = null
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {
    var list: ArrayList<ContentModel> = arrayListOf()
    var map: HashMap<Int, ContentModel> = hashMapOf()

    fun onCreate(){

    }

    //Удаление из баз
    fun deleteModel(intent : Intent){
        firebaseDb.collection("db")
            .document(intent.extras!!.getString("op").toString())
            .delete()
        db.appDataBaseFir().deleteWord(intent.extras!!.getSerializable("model") as ModelGallery)
    }

    //Добовление
    fun addModel(text: String, map: HashMap<Int, ContentModel>){
        map.map {
            list.add(ContentModel(it.key, it.value.text, it.value.isCheck))
        }

        //Заполнение модели
        val dataItem = ModelGallery(
            repId(), text,
            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), list
        )

        //Запись в баз данных
        db.appDataBaseFir().insertModel(dataItem)
        firebaseDb.collection("db").add(dataItem)
    }

    fun addList(id: Int) {
        launch {
            val modDb = db.appDataBaseFir().getAllModel()
            if (modDb.isNotEmpty()) {
                val model = modDb.firstOrNull { it.id == id }
                if (model != null){
                    model.arrey?.let {
                        view!!.updateAdapter(it)
                        it.map { itm -> map[itm.id!!] = itm }
                        view!!.titleName(model.title.toString())
                    }
                }
            }
        }
    }

    //Генерация id
    private fun repId() = Random().nextInt(500) + 20
}