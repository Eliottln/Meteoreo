package com.iut.meteoreo.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iut.meteoreo.databinding.FragmentChartBinding
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*

class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailsViewModel = ViewModelProvider(requireActivity()).get(DayDetailsViewModel::class.java)
        binding = FragmentChartBinding.inflate(inflater, container, false)


        binding.graphView.animate()
        binding.graphView.viewport.isScrollable = true
        binding.graphView.viewport.isScalable = true
        binding.graphView.viewport.setScalableY(true)
        binding.graphView.viewport.setScrollableY(true)
//        series.color = R.color.purple_200

        detailsViewModel.daysMeasures.observe(viewLifecycleOwner) {
            val series: LineGraphSeries<DataPoint> = LineGraphSeries()
            val calendar = Calendar.getInstance()
            for ((index, item) in it.withIndex()) {
                calendar.timeInMillis = item.timestamp?.toLong()!!
                series.appendData(DataPoint(calendar.time, item.temperature ?: 0.0), false, index + 1)
            }
            binding.graphView.addSeries(series)
            binding.graphView.gridLabelRenderer.setHumanRounding(false)

        }

        return binding.root
    }

    fun setGraph() {

    }

}