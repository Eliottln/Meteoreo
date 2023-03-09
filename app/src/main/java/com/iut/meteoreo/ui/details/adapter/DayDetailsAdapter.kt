package com.iut.meteoreo.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iut.meteoreo.data.Measure
import com.iut.meteoreo.databinding.ItemDayDetailsBinding
import com.iut.meteoreo.extensions.timestampToHour

class DayDetailsAdapter : RecyclerView.Adapter<DayDetailsAdapter.ViewHolder>() {

    var measuresList: ArrayList<Measure> = arrayListOf()

    inner class ViewHolder(private val binding: ItemDayDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(measure: Measure) = binding.apply {

            binding.hour.text = measure.timestamp?.toLong()?.timestampToHour()
            binding.temperatureValue.text = "${measure.temperature}°C"
            binding.wetValue.text = "${measure.humidity}%"
            binding.windValue.text = "${measure.windSpeed} km/h"
            binding.uvValue.text = "${measure.uvValue}"
            binding.pressureValue.text = "${measure.airPressure} hPa"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDayDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(measuresList[position])
    }

    override fun getItemCount(): Int {
        return measuresList.size
    }
}