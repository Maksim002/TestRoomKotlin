package com.example.testroomkotlin.adapter

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ContentModel
import com.example.testroomkotlin.db.model.ModelGallery
import kotlinx.android.synthetic.main.item_content.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*

class ContentViewHolder(itemView: View, var  listener: ContentAdapter.Listener) : RecyclerView.ViewHolder(itemView) {

    fun bind(dataItem: ContentModel, holder: ContentViewHolder) = with(holder.itemView) {
        name_edit_text.addTextChangedListener {
            if (it != null){
                listener.setOnClickListener(it.toString(), holder.adapterPosition)
            }
        }
    }
}