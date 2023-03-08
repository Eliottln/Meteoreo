package com.iut.meteoreo.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.iut.meteoreo.data.DayTemperature

class DayDetailsViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    private var _timestamp: String = ""
    private val _lastDaysMeasures = MutableLiveData<ArrayList<DayTemperature>>()
    val lastDaysMeasures: LiveData<ArrayList<DayTemperature>> = _lastDaysMeasures

    fun getDayMeasures(timestamp: String) {
        _timestamp = timestamp


    }
}