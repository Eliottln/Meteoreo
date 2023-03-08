package com.iut.meteoreo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iut.meteoreo.databinding.FragmentHomeBinding
import com.iut.meteoreo.ui.home.adapter.HomeDaysAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val daysListAdapter = HomeDaysAdapter(requireContext()) {

        }
        binding.daysList.adapter = daysListAdapter
        binding.daysList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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
        homeViewModel.getLastDays()
//        homeViewModel.fakeValue()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}