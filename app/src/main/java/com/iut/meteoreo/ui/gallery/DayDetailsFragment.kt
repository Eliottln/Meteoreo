package com.iut.meteoreo.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iut.meteoreo.databinding.FragmentDayDetailsBinding

class DayDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDayDetailsBinding
    private var timestamp: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel = ViewModelProvider(this).get(DayDetailsViewModel::class.java)
        binding = FragmentDayDetailsBinding.inflate(inflater, container, false)
        arguments?.let {
            timestamp = it.getString("timestamp")
        }

        galleryViewModel.lastDaysMeasures.observe(viewLifecycleOwner) {

        }

        return binding.root
    }

}