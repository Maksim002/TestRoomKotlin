package com.example.testroomkotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testroomkotlin.R
import com.example.testroomkotlin.pager.SelectionPagerAdapter
import com.example.testroomkotlin.db.AppDataBase
import com.example.testroomkotlin.ui.main.fragment.all.AllDatabaseFragment
import com.example.testroomkotlin.ui.main.fragment.mi.MiDatabaseFragment
import com.google.firebase.firestore.FirebaseFirestore
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
        }

        bottomAll.setOnClickListener {
            profilePager.currentItem = 1
        }
        profilePager.adapter = adapter
    }
}