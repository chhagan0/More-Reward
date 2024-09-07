package com.cxzcodes.newappspin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cxzcodes.newappspin.R

class MSGAdapter(private val numbers: List<String>) :
    RecyclerView.Adapter<MSGAdapter.NumberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.win_msg_layout, parent, false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(numbers[position])
    }

    override fun getItemCount(): Int = numbers.size

    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberTextView: TextView = itemView.findViewById(R.id.tvWithdraw)

        fun bind(number: String) {
            numberTextView.text = number
        }
    }
}
