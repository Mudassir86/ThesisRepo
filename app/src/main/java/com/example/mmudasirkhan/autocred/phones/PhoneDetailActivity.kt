package com.example.mmudasirkhan.autocred.phones

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.mmudasirkhan.autocred.R

class PhoneDetailActivity : AppCompatActivity() {

    companion object {
        private const val PHONE_KEY = "phone"

        fun newIntent(context: Context, phone: String): Intent {
            val intent = Intent(context, PhoneDetailActivity::class.java)
            intent.putExtra(PHONE_KEY, phone)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_detail)

        val textView = findViewById<TextView>(R.id.name)
        textView.text = intent.getStringExtra(PHONE_KEY)
    }


}
