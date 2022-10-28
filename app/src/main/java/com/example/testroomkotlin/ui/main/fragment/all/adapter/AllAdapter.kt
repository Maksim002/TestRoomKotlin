package com.example.testroomkotlin.ui.main.fragment.all.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ModelGallery
import kotlinx.android.synthetic.main.item_all.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllAdapter(var listener: Listener) : RecyclerView.Adapter<AllViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AllViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_all, parent, false
        )
    )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        holder.bind(item[position], holder)
        holder.itemView.setOnClickListener {
            if (item[position].id == 9379992){
                listener.setOnClickListener(position, holder.itemView, false)
            }else{
                listener.setOnClickListener(position, holder.itemView, true)
            }
        }
        holder.itemView.clear.setOnClickListener {
            listener.setOnClickItem(position)
        }
    }

    interface Listener{
        fun setOnClickListener(position: Int, view: View, boolean: Boolean)
        fun setOnClickItem(position: Int)
    }
}