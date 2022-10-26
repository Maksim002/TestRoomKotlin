package com.example.testroomkotlin.ui.gallery

import com.example.testroomkotlin.db.model.ModelGallery

interface GalleryView {
    fun updateAdapter(modelGallery: ArrayList<ModelGallery> = arrayListOf())
//    fun deleteModel(modelGallery: ModelGallery? = null)
//    fun addListMod(modelGallery: ModelGallery)
}