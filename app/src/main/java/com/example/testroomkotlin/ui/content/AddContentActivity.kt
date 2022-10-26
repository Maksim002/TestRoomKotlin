package com.example.testroomkotlin.ui.content

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testroomkotlin.R
import com.example.testroomkotlin.adapter.ContentAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ContentModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddContentActivity : AppCompatActivity(), AddContentView {
    private lateinit var adapters: ContentAdapter
    private var listMap: ArrayList<ContentModel> = arrayListOf()

    private lateinit var firebaseDb: FirebaseFirestore
    private lateinit var db: AppDataBase

    private lateinit var presenter: AddContentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_content)

        //База firebase
        firebaseDb = FirebaseFirestore.getInstance()
        //База mobile
        db = AppDataBase.instance(this)
        presenter = AddContentPresenter(db, firebaseDb, this)
        presenter.onCreate()

        //Инцелезация адаптора
        adapters = ContentAdapter(object : ContentAdapter.Listener {
            //Добовляет моделб
            override fun setOnClickListener(text: String, int: Int) {
                presenter.map[int] = ContentModel(int, text, false)
            }

            //Редактирует модель
            override fun setOnClickListener(isCheck: Boolean, text: String, int: Int) {
                presenter.map[int] = ContentModel(int, text, isCheck)
            }

            //Удаляет модель
            override fun setOnClickListenerDelete(ntMap: ContentModel, intMap: Int) {}
        })

        if (conValue() == -1) {
            //Новое добовление параметров
            adapters.setData(listMap)
            refreshFile.isVisible = false
            listMap.add(ContentModel(0,"", false))
        }else{
            presenter.addList(conValue())
        }

        //Приравниеваение дадатора к ресайклу
        recyclerView.adapter = adapters

        //Клик на добоыление новых полей.
        saveItem.setOnClickListener {
            // TODO: vremenaz mera
            val text = titleEditText.text.toString()
            if (text != "") {
                CoroutineScope(Dispatchers.IO).launch {
                    presenter.addModel(text, presenter.map)
                    finish()
                }
            } else {
                Toast.makeText(this, "Название базы введи потом твкай!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        addItem.setOnClickListener {
            listMap.add(ContentModel(0,"", false))
            adapters.setData(listMap)
        }
    }

    override fun updateAdapter(arrey: List<ContentModel>) {
        adapters.setData(arrey)
    }

    override fun titleName(name: String) = runOnUiThread{
        titleEditText.setText(name)
    }

    //Проверка новое добовление или редактирование старого
    private fun conValue() = intent.extras!!.getInt("value")

    //Функция системная возврощает на предыдущий экран
    override fun onBackPressed() {
        if (!refreshFile.isChecked)
        super.onBackPressed()
    }
}