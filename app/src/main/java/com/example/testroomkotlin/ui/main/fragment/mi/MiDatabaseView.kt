package com.example.testroomkotlin.ui.main.fragment.mi

import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.db.model.ModelGallery

interface MiDatabaseView {
    fun updateAdapter(modelGallery: Model? = null)
    fun deleteModel(modelGallery: Model? = null)
    fun addListMod(modelGallery: Model)
}