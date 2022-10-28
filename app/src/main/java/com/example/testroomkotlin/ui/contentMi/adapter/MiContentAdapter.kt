package com.example.testroomkotlin.ui.contentMi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ContentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MiContentAdapter(var listener: Listener) : RecyclerView.Adapter<MiContentViewHolder>() {

    private var item: List<ContentModel>
    private var booleanVal: Boolean? = true

    init {
        item = ArrayList()
    }
    fun setData(items: List<ContentModel>) {
        CoroutineScope(Dispatchers.Main).launch{
           item = items
           notifyItemChanged(itemCount)
           notifyItemChanged(itemCount, item)
        }
    }

    fun valueBol(boolean: Boolean = true){
        booleanVal = boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MiContentViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_content_mi, parent, false
        ), listener
    )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: MiContentViewHolder, position: Int) {
        holder.bind(item[position], holder, booleanVal)
    }

    interface Listener{
        fun setOnClickListener(text: String, int: Int)
        fun setOnClickListener(isCheck: Boolean, text: String, int: Int)
        fun setOnClickListenerDelete(ntMap: ContentModel, list: Int)
    }
}