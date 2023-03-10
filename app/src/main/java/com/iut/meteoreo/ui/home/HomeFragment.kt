package com.iut.meteoreo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iut.meteoreo.R
import com.iut.meteoreo.databinding.FragmentHomeBinding
import com.iut.meteoreo.ui.home.adapter.HomeDaysAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val daysListAdapter = HomeDaysAdapter {
            findNavController().navigate(R.id.action_nav_home_to_nav_day_details, bundleOf("timestamp" to it))
        }
        binding.daysList.adapter = daysListAdapter
        binding.daysList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        homeViewModel.lastMeasure.observe(viewLifecycleOwner) {
            binding.actualTemperature.text = "${it.temperature}°C"
            binding.wetValue.text = "${it.humidity}%"
            binding.windValue.text = "${it.temperature} km/h"
            binding.uvValue.text = "${it.temperature}"
            binding.pressureValue.text = "${it.airPressure} hPa"
        }

        homeViewModel.lastDaysMeasures.observe(viewLifecycleOwner) {
            daysListAdapter.daysList = it
            daysListAdapter.notifyDataSetChanged()
        }

        homeViewModel.getStation(1)
//        homeViewModel.fakeValue()
        return binding.root
    }

}