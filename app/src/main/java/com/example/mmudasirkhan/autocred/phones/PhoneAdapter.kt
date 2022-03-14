package com.example.mmudasirkhan.autocred.phones

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mmudasirkhan.autocred.R

class PhoneAdapter(
    private val context: Context,
    private val listener: ViewHolderListener,
    private val list: List<String>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = list.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhoneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_phone, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhoneViewHolder -> {
                holder.bind(list[position])
                holder.onClick { view, position, type ->
                    listener.onClick(view, position, type)
                }
            }
        }
    }

    private class PhoneViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView by lazy {
            view.findViewById(R.id.name) as TextView
        }

        fun bind(viewModel: String) {
            textView.text = viewModel
        }
    }
}




