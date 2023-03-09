package com.iut.meteoreo.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iut.meteoreo.R
import com.iut.meteoreo.databinding.FragmentDayDetailsBinding
import com.iut.meteoreo.extensions.timestampToDate
import com.iut.meteoreo.ui.gallery.adapter.DayDetailsAdapter

class DayDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDayDetailsBinding
    private var timestamp: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailsViewModel = ViewModelProvider(this).get(DayDetailsViewModel::class.java)
        binding = FragmentDayDetailsBinding.inflate(inflater, container, false)
        arguments?.let {
            timestamp = it.getLong("timestamp")
        }

        val daysListAdapter = DayDetailsAdapter {
        }
        binding.buttonGraph.setOnClickListener {
            findNavController().navigate(R.id.action_nav_day_details_to_nav_chart)
        }
        binding.date.text = timestamp?.timestampToDate()
        binding.dayRecyclerview.adapter = daysListAdapter
        binding.dayRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        detailsViewModel.daysMeasures.observe(viewLifecycleOwner) {
            daysListAdapter.measuresList = it
            daysListAdapter.notifyDataSetChanged()
        }

        timestamp?.let { detailsViewModel.getDayMeasures(it) }

        return binding.root
    }

}