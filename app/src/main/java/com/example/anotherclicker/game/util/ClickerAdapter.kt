package com.example.anotherclicker.game.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anotherclicker.R
import com.example.anotherclicker.database.entities.Clicker
import java.lang.Exception

class ClickerAdapter: RecyclerView.Adapter<ClickerAdapter.ViewHolder>() {
    var data = listOf<Clicker>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ClickerAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.infoBox.text = "${item.cost}$: ${item.name}"
        holder.amountBox.text = item.amount.toString()

        holder.clickerImage.setImageResource(when (item.id) {
            1 -> R.drawable.ic_white_clicker
            2 -> R.drawable.ic_green_clicker
            3 -> R.drawable.ic_orange_clicker
            else -> throw Exception()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.clicker_item, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoBox: TextView = itemView.findViewById(R.id.info_string)
        val amountBox: TextView = itemView.findViewById(R.id.amount_string)
        val clickerImage: ImageView = itemView.findViewById(R.id.clicker_image)
    }
}