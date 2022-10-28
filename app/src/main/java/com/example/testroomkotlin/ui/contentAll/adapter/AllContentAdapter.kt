package com.example.testroomkotlin.ui.contentAll.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.R
import com.example.testroomkotlin.db.model.ContentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllContentAdapter(var listener: Listener) : RecyclerView.Adapter<AllContentViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AllContentViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_content, parent, false
        ), listener
    )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: AllContentViewHolder, position: Int) {
        holder.bind(item[position], holder, booleanVal)
    }

    interface Listener{
        fun setOnClickListener(text: String, int: Int)
        fun setOnClickListener(isCheck: Boolean, text: String, int: Int)
        fun setOnClickListenerDelete(ntMap: ContentModel, list: Int)
    }
}