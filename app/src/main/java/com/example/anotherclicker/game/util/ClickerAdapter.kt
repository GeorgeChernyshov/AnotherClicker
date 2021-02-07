package com.example.anotherclicker.game.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.anotherclicker.R
import com.example.anotherclicker.database.entities.Clicker
import com.example.anotherclicker.databinding.ClickerItemBinding
import com.example.anotherclicker.generated.callback.OnClickListener
import java.lang.Exception

class ClickerAdapter(val clickListener: ClickerItemListener): ListAdapter<Clicker, ClickerAdapter.ViewHolder>(ClickerDiffCallback()) {
    override fun onBindViewHolder(holder: ClickerAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickerAdapter.ViewHolder = ViewHolder.from(parent, viewType)

    class ViewHolder private constructor(val binding: ClickerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, viewType: Int): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ClickerItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: Clicker, clickListener: ClickerItemListener) {
            binding.clicker = item
            binding.clickListener = clickListener
            binding.infoString.text = "${item.cost}$: ${item.name}"
            binding.amountString.text = item.amount.toString()

            binding.clickerImage.setImageResource(when (item.id) {
                1 -> R.drawable.ic_white_clicker
                2 -> R.drawable.ic_green_clicker
                3 -> R.drawable.ic_orange_clicker
                else -> throw Exception()
            })
        }
    }
}

class ClickerDiffCallback: DiffUtil.ItemCallback<Clicker>() {
    override fun areItemsTheSame(oldItem: Clicker, newItem: Clicker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Clicker, newItem: Clicker): Boolean {
        return oldItem == newItem
    }
}

class ClickerItemListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(clicker: Clicker) = clickListener(clicker.id)
}