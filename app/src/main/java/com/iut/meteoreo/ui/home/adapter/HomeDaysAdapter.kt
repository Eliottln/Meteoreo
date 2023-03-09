package com.iut.meteoreo.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iut.meteoreo.data.DayTemperature
import com.iut.meteoreo.databinding.ItemPreviousDaysBinding
import com.iut.meteoreo.extensions.timestampStartOfDay
import com.iut.meteoreo.extensions.toDayOfWeek

class HomeDaysAdapter(private var onItemClicked: (timestamp: Long) -> Unit) : RecyclerView.Adapter<HomeDaysAdapter.ViewHolder>() {

    var daysList: ArrayList<DayTemperature> = arrayListOf()

    inner class ViewHolder(private val binding: ItemPreviousDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: DayTemperature) = binding.apply {

            binding.day.text = day.day.toDayOfWeek()
            binding.temp.text = "${day.maxTemperature?.temperature ?: "?"}°C / ${day.minTemperature?.temperature ?: "?"}°C"

            root.setOnClickListener {
                onItemClicked(day.day.timestampStartOfDay())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPreviousDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(daysList[position])
    }

    override fun getItemCount(): Int {
        return daysList.size
    }
}