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
        val listRef = database.child("stations")
        val query = listRef.orderByChild("id").equalTo(id.toDouble())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val tmp = data.getValue(Station::class.java)
                    if (tmp?.id == 1) {
                        _station.value = tmp
                        getActualMeasure()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("QUERYFIREBASE", "$error")
            }
        })
    }

    fun getActualMeasure() {
        val list = _station.value?.measures
        Log.d("QUERYFIREBASE", "getactual: $list")
        _lastMeasure.value = list?.get(0)
    }

    fun fakeValue() {
        val measure = arrayListOf(Measure(System.currentTimeMillis(), 12.0, 14.0, 45.0, 1020.0, 30.0, 1.2))
        val station = Station(1, "Extérieur", measure)
        database.child("stations").push().setValue(station)
    }

}