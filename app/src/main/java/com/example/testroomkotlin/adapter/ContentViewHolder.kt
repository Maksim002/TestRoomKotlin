package com.example.testroomkotlin.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ContentModel
import com.example.testroomkotlin.db.model.ModelGallery
import kotlinx.android.synthetic.main.item_content.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*

class ContentViewHolder(itemView: View, var  listener: ContentAdapter.Listener) : RecyclerView.ViewHolder(itemView) {

    fun bind(dataItem: ContentModel, holder: ContentViewHolder, booleanVal: Boolean? = true) = with(holder.itemView) {
        name_edit_text.isFocusable = booleanVal!!
        refreshFile.isChecked = dataItem.isCheck!!
        if (booleanVal) refreshFile.isVisible = false else booleanVal
        if (dataItem.text != ""){
            name_edit_text.setText(dataItem.text)
        }
        refreshFile.setOnCheckedChangeListener { _, isChecked ->
            listener.setOnClickListener(isChecked, name_edit_text.text.toString(), dataItem.id!!)
        }
        name_edit_text.addTextChangedListener {
            if (it.toString() != ""){
                listener.setOnClickListener(it.toString(), if(dataItem.id != 0) dataItem.id!! else holder.adapterPosition)
            }
        }

        deleteIm.setOnClickListener {
            listener.setOnClickListenerDelete(dataItem, holder.adapterPosition)
        }
    }
}