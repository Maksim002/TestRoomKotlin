package com.example.testroomkotlin.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.R

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var title = itemView.findViewById<TextView>(R.id.titleTextView)

    fun bind(dataItem: Model) {
        title.text = dataItem.title
    }
}