package com.example.testroomkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.db.model.Model
import com.example.testroomkotlin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryAdapter(var listener: Listener) : RecyclerView.Adapter<MainViewHolder>() {

    private var item: List<Model>
    init {
        item = ArrayList()
    }
    fun setData(items: List<Model>) {
        CoroutineScope(Dispatchers.Main).launch{
           item = items
           notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_sample, parent, false
        )
    )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(item[position])
        holder.itemView.setOnClickListener {
            listener.setOnClickListener(position, holder.itemView)
        }
    }

    interface Listener{
        fun setOnClickListener(position: Int, view: View)
    }
}