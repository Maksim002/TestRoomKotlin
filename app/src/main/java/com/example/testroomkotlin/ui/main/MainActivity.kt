package com.example.testroomkotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.testroomkotlin.R
import com.example.testroomkotlin.pager.SelectionPagerAdapter
import com.example.testroomkotlin.ui.main.fragment.all.AllDatabaseFragment
import com.example.testroomkotlin.ui.main.fragment.mi.MiDatabaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = SelectionPagerAdapter(this.supportFragmentManager)
        adapter.addFragment(MiDatabaseFragment(), "Mi")
        adapter.addFragment(AllDatabaseFragment(), "All")

        bottomMi.setOnClickListener {
            profilePager.currentItem = 0
            textTitle.text = "Мои заметки"
            logColor(R.color.color_text_w_pink)
        }

        bottomAll.setOnClickListener {
            profilePager.currentItem = 1
            textTitle.text = "Общие заметки"
            logColor(R.color.color_text_w)
        }
        profilePager.adapter = adapter
    }

    private fun logColor(coloInt: Int){
        textTitle.setTextColor(ContextCompat.getColor(this, R.color.color_text_w_alpha))
        textTitle.setBackgroundColor(ContextCompat.getColor(this, coloInt))
        window.statusBarColor = resources.getColor(coloInt)
    }
}