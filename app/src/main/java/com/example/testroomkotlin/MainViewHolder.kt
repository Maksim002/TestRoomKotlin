package com.example.testroomkotlin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.db.Model
import kotlinx.android.synthetic.main.recycler_model.view.*

class MainViewHolder (itemView: View,  var listener: Listener) : RecyclerView.ViewHolder(itemView) {

    fun bind(dataItem: Model, holder: MainViewHolder) {
        holder.itemView.recycler_model.text = dataItem.title

        holder.itemView.setOnClickListener {
            listener.setOnClickListenerDelete(dataItem)
        }
    }

    interface Listener{
        fun setOnClickListenerDelete(dataItem: Model)
    }
}