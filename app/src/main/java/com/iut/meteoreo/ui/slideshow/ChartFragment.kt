package com.iut.meteoreo.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iut.meteoreo.databinding.FragmentChartBinding
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chartViewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        binding = FragmentChartBinding.inflate(inflater, container, false)

        // on below line we are initializing
        // our variable with their ids.
        binding.graphView

        // on below line we are adding data to our graph view.
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 3.0),
                DataPoint(2.0, 4.0),
                DataPoint(3.0, 9.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 3.0),
                DataPoint(6.0, 6.0),
                DataPoint(7.0, 1.0),
                DataPoint(8.0, 2.0)
            )
        )

        // on below line adding animation
        binding.graphView.animate()

        // on below line we are setting scrollable
        // for point graph view
        binding.graphView.viewport.isScrollable = true

        // on below line we are setting scalable.
        binding.graphView.viewport.isScalable = true

        // on below line we are setting scalable y
        binding.graphView.viewport.setScalableY(true)

        // on below line we are setting scrollable y
        binding.graphView.viewport.setScrollableY(true)

        // on below line we are setting color for series.
//        series.color = R.color.purple_200

        // on below line we are adding
        // data series to our graph view.
        binding.graphView.addSeries(series)

        return binding.root
    }

}