package com.example.testroomkotlin.ui.main.fragment.all.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ModelGallery
import kotlinx.android.synthetic.main.item_mi.view.*

class AllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(dataItem: ModelGallery, holder: AllViewHolder) = with(holder.itemView) {
        title_id.text = dataItem.title
        if (dataItem.id == 9379992){
            image_id.setImageDrawable(resources.getDrawable(R.drawable.icons8_03))
            clear.visibility = View.INVISIBLE
            time_id.isVisible = false
        }else{
            time_id.text = dataItem.time
            image_id.setImageDrawable(resources.getDrawable(R.drawable.icons8_dock))
            clear.visibility = View.VISIBLE
            time_id.isVisible = true
        }
    }
}