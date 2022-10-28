package com.example.testroomkotlin.ui.main.fragment.all

import com.example.testroomkotlin.db.model.ModelGallery

interface AllDatabaseView {
    fun updateAdapter(modelGallery: ModelGallery? = null)
    fun deleteModel(modelGallery: ModelGallery? = null)
    fun addListMod(modelGallery: ModelGallery)
}