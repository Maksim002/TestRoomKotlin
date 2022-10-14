package com.example.testroomkotlin.adapter

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ModelGallery
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(dataItem: ModelGallery, holder: GalleryViewHolder) = with(holder.itemView) {
        title_id.text = dataItem.title
        if (dataItem.id == 9379992){
            image_id.setImageDrawable(resources.getDrawable(R.drawable.icons8_03))
            clear.visibility = View.INVISIBLE
        }else{
            time_id.text = dataItem.time
            image_id.setImageDrawable(resources.getDrawable(R.drawable.icons8_dock))
            clear.visibility = View.VISIBLE
        }
    }
}