package com.iut.meteoreo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.iut.meteoreo.data.Measure
import com.iut.meteoreo.data.Station

class HomeViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    private val _station = MutableLiveData<Station?>()
    val station: LiveData<Station?> = _station
    private val _lastMeasure = MutableLiveData<Measure>()
    val lastMeasure: LiveData<Measure> = _lastMeasure

    fun getStation(id: Int) {
        val listRef = database.child("stations").child("$id")

        listRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val measures = mutableListOf<Measure>()
                snapshot.child("measures").children.forEach {
                    val measure = it.getValue(Measure::class.java)
                    measure?.let { it1 -> measures.add(it1) }
                }
                _station.value = Station(snapshot.child("name").value as String, measures)
                getActualMeasure()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("QUERYFIREBASE", "$error")
            }
        })
    }

    fun getActualMeasure() {
        val list = _station.value?.measures
        _lastMeasure.value = list?.last()
    }

    fun fakeValue() {
        val stationPath = database.child("stations").child("1")
        stationPath.child("name").setValue("Extérieur")
        stationPath.child("measures").push().setValue(Measure(System.currentTimeMillis(), -20.0, 22.3, 60.0, 300.0, 4.0, 1.9))
    }

}