package com.iut.meteoreo.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iut.meteoreo.data.DayTemperature
import com.iut.meteoreo.databinding.ItemPreviousDaysBinding

class HomeDaysAdapter(val context: Context, private var onItemClicked: ((id: Int) -> Unit)): RecyclerView.Adapter<HomeDaysAdapter.ViewHolder>() {

    var daysList: ArrayList<DayTemperature> = arrayListOf()

    inner class ViewHolder(private val binding: ItemPreviousDaysBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(day: DayTemperature) = binding.apply {

            binding.day.text = day.day
            binding.temp.text = "${day.maxTemperature}°C / ${day.minTemperature}°C"

            root.setOnClickListener {
//                onItemClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPreviousDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(daysList[position])
    }

    override fun getItemCount(): Int {
        return daysList.size
    }
}