package com.example.testroomkotlin.ui.content

import com.example.testroomkotlin.db.model.ContentModel

interface AddContentView {
    fun updateAdapter(arrey: List<ContentModel>)
    fun titleName(name: String)
}