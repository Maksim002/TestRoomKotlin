package com.example.testroomkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ContentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContentAdapter(var listener: Listener) : RecyclerView.Adapter<ContentViewHolder>() {

    private var item: List<ContentModel>
    private var booleanVal: Boolean? = true

    init {
        item = ArrayList()
    }
    fun setData(items: List<ContentModel>) {
        CoroutineScope(Dispatchers.Main).launch{
           item = items
           notifyItemChanged(itemCount)
        }
    }

    fun valueBol(boolean: Boolean = true){
        booleanVal = boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContentViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_content, parent, false
        ), listener
    )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(item[position], holder, booleanVal)
    }

    interface Listener{
        fun setOnClickListener(text: String, int: Int)
    }
}