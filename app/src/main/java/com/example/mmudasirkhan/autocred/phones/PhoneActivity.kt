package com.example.mmudasirkhan.autocred.phones

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.mmudasirkhan.autocred.R

class PhoneActivity : AppCompatActivity(), ViewHolderListener {

    val list = listOf("Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X")

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PhoneActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = PhoneAdapter(this, this, list)
        recyclerView.layoutManager =  LinearLayoutManager(this)
    }

    override fun onClick(view: View, position: Int, type: Int) {
        val intent = PhoneDetailActivity.newIntent(this, list[position])
        startActivity(intent)
    }
}
