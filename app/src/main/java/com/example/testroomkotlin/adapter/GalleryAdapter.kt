package com.example.testroomkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ModelGallery
import kotlinx.android.synthetic.main.item_gallery.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryAdapter(var listener: Listener) : RecyclerView.Adapter<GalleryViewHolder>() {

    private var item: List<ModelGallery>
    init {
        item = ArrayList()
    }
    fun setData(items: List<ModelGallery>) {
        CoroutineScope(Dispatchers.Main).launch{
           item = items
           notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_gallery, parent, false
        )
    )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(item[position], holder)
        holder.itemView.setOnClickListener {
            if (item[position].id == 9379992){
                listener.setOnClickListener(position, holder.itemView)
            }
        }
        holder.itemView.clear.setOnClickListener {
            listener.setOnClickItem(position)
        }
    }

    interface Listener{
        fun setOnClickListener(position: Int, view: View)
        fun setOnClickItem(position: Int)
    }
}