package com.example.testroomkotlin.ui.content

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.ContentAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ContentModel
import com.example.testroomkotlin.db.model.ModelGallery
import com.example.testroomkotlin.ui.gallery.GalleryRepository
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddContentActivity : AppCompatActivity() {

    private lateinit var adapters: ContentAdapter
    private var map: HashMap<Int, String> = hashMapOf()
    private var listMap: ArrayList<ContentModel> = arrayListOf()

    private lateinit var firebaseDb: FirebaseFirestore
    private lateinit var db: AppDataBase

    private lateinit var presenter: GalleryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_content)

        firebaseDb = FirebaseFirestore.getInstance()
        db = AppDataBase.instance(this)
        presenter = GalleryRepository(db, firebaseDb)

        initClick()

        val list: ArrayList<ContentModel> = arrayListOf()

        adapters = ContentAdapter(object : ContentAdapter.Listener {
            override fun setOnClickListener(text: String, int: Int) {
                map[int] = text
            }
        })

        if (!intent.extras!!.getBoolean("value")) {
            adapters.setData(list)
            refreshFile.isVisible = false
            list.add(ContentModel(""))
        } else {
            val data = intent.extras!!.getSerializable("model") as ModelGallery
            data.arrey?.map {list.add(it)}
            data.arrey?.map {listMap.add(it)}
            titleEditText.setText(data.title)
            adapters.setData(data.arrey ?: arrayListOf())
            adapters.valueBol(false)
            bottomSave.isVisible = false
            titleEditText.isEnabled = false
            refreshFile.isVisible = true
        }

        recyclerView.adapter = adapters

        saveItem.setOnClickListener {
            // TODO: vremenaz mera
            val text = titleEditText.text.toString()
            if (text != "") {
                CoroutineScope(Dispatchers.IO).launch {
                    map.map {
                        listMap.add(ContentModel(it.value))
                    }
                    val dataItem = ModelGallery(
                        Random().nextInt(500) + 20, text,
                        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), listMap
                    )
                    db.appDataBaseFir().insertModel(dataItem)
                    firebaseDb.collection("db")
                        .add(dataItem)
                    finish()
                }
            } else {
                Toast.makeText(this, "Название базы введи потом твкай!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        addItem.setOnClickListener {
            list.add(ContentModel(""))
            adapters.setData(list)
        }
    }

    private fun initClick() = with(CoroutineScope(Dispatchers.IO)) {
        refreshFile.isChecked = false
        refreshFile.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                adapters.valueBol(isChecked)
                launch {
                    firebaseDb.collection("db")
                        .document(intent.extras!!.getString("op").toString())
                        .delete()
                    db.appDataBaseFir().deleteWord(intent.extras!!.getSerializable("model") as ModelGallery)
                }
                bottomSave.isVisible = true
                titleEditText.isEnabled = true
            }else{
                adapters.valueBol(false)
                bottomSave.isVisible = false
                titleEditText.isEnabled = false
            }
            recyclerView.adapter = adapters
        }
    }

    override fun onBackPressed() {
        if (!refreshFile.isChecked)
        super.onBackPressed()
    }
}