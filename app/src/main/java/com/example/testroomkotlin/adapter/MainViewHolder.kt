package com.example.kotlinroom.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.Model
import com.example.testroomkotlin.R

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var title = itemView.findViewById<TextView>(R.id.titleTextView)

    fun bind(dataItem: Model) {
        title.text = dataItem.title
    }
}