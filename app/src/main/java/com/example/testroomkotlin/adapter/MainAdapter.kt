package com.example.kotlinroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testroomkotlin.Model
import com.example.testroomkotlin.R

class MainAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    private var items: List<Model>
    init { items = ArrayList()
    }
    fun setData(items: List<Model>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_sample, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }
}