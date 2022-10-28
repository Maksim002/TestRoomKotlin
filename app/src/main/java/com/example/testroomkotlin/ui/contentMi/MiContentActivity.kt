package com.example.testroomkotlin.ui.contentMi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.db.model.ContentModel
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.ui.contentMi.adapter.MiContentAdapter
import com.example.testroomkotlin.ui.main.fragment.mi.MiDatabaseRepository
import kotlinx.android.synthetic.main.activity_add_content_mi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MiContentActivity : AppCompatActivity() {

    private lateinit var adapters: MiContentAdapter
    private var map: HashMap<Int, ContentModel> = hashMapOf()
    private var listMap: ArrayList<ContentModel> = arrayListOf()
    private val list: ArrayList<ContentModel> = arrayListOf()
    private lateinit var model: Model

    private lateinit var db: AppDataBase
    private lateinit var presenter: MiDatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemeMi)
        setContentView(R.layout.activity_add_content_mi)

        //Инцилизация базы мабилки
        db = AppDataBase.instance(this)
        presenter = MiDatabaseRepository(db)

        initClick()

        //Инцелезация адаптора
        adapters = MiContentAdapter(object : MiContentAdapter.Listener {
            //Добовляет моделб
            override fun setOnClickListener(text: String, int: Int) {
                map[int] = ContentModel(int, text, false)
            }

            //Редактирует модель
            override fun setOnClickListener(isCheck: Boolean, text: String, int: Int) {
                map[int] = ContentModel(int, text, isCheck)
                bottomSave.isVisible = true
                addItem.isVisible = false
            }

            //Удаляет модель
            override fun setOnClickListenerDelete(ntMap: ContentModel, intMap: Int) {
                try {
                    map.remove(ntMap.id!!)
                    listMap.removeAt(intMap)
                    list.removeAt(intMap)
                    adapters.setData(listMap)
                    bottomSave.isVisible = true
                    addItem.isVisible = false
                    adapters.notifyItemRemoved(intMap)
                }catch (e: Exception){e.printStackTrace()}
            }
        })

        if (!conValue()) {
            //Новое добовление параметров
            adapters.setData(list)
            refreshFile.isVisible = false
            list.add(ContentModel(0,"", false))
        } else {
            //Полечение данных с другова фрагмента для редактирования
            val data = intent.extras!!.getSerializable("model") as Model
            data.arrey?.map {listMap.add(ContentModel(repId(), it.text, it.isCheck))}
            model = data
            listMap.map {
                map[it.id!!.toInt()] = ContentModel(it.id, it.text, it.isCheck)
                list.add(ContentModel(it.id, it.text, it.isCheck))
            }
            titleEditText.setText(data.title)
            adapters.setData(listMap)
            adapters.valueBol(false)
            bottomSave.isVisible = false
            titleEditText.isEnabled = false
            refreshFile.isVisible = true
        }
        //Приравниеваение дадатора к ресайклу
        recyclerView.adapter = adapters

        //Клик на добоыление новых полей.
        saveItem.setOnClickListener {
            // TODO: vremenaz mera
            val text = titleEditText.text.toString()
            if (text != "") {
                CoroutineScope(Dispatchers.IO).launch {
                    addModel(text)
                }
            } else {
                Toast.makeText(this,
                    "Название базы введи потом твкай!!!", Toast.LENGTH_SHORT).show()
            }
        }

        addItem.setOnClickListener {
            list.add(ContentModel(0,"", false))
            adapters.setData(list)
        }
    }

    //Добовление
    private fun addModel(text: String){
        listMap.clear()
        list.clear()
        map.map {
            listMap.add(ContentModel(it.key, it.value.text, it.value.isCheck))
            list.add(ContentModel(it.key, it.value.text, it.value.isCheck))
        }

        //Заполнение модели
        val dataItem = Model(
            repId(), text,
            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), listMap
        )

        //Запись в баз данных
        if (::model.isInitialized){
            db.appDataBase().update(Model(model.id, model.title, model.time, listMap))
        }else db.appDataBase().insertModel(dataItem)
        finish()
    }

    private fun initClick() {
        refreshFile.isChecked = false
        // Слушательл включения чек бокса
        refreshFile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                adapters.valueBol(isChecked)
                bottomSave.isVisible = true
                titleEditText.isEnabled = true
                addItem.isVisible = true
            }else{
                adapters.valueBol(false)
                bottomSave.isVisible = false
                titleEditText.isEnabled = false
            }
            recyclerView.adapter = adapters
        }
    }

    //Генерация id
    private fun repId() = Random().nextInt(500) + 20
    //Проверка новое добовление или редактирование старого
    private fun conValue() = intent.extras!!.getBoolean("value")

    //Функция системная возврощает на предыдущий экран
    override fun onBackPressed() {
        if (!refreshFile.isChecked)
            super.onBackPressed()
    }
}