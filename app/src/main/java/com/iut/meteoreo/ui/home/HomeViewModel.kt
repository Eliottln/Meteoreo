package com.iut.meteoreo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.iut.meteoreo.data.Measure
import com.iut.meteoreo.data.Station
import java.time.LocalDate

class HomeViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    private var _stationId = 0
    private val _lastDaysMeasures = MutableLiveData<ArrayList<Measure>>()
    val lastDaysMeasures: LiveData<ArrayList<Measure>> = _lastDaysMeasures
    private val _lastMeasure = MutableLiveData<Measure>()
    val lastMeasure: LiveData<Measure> = _lastMeasure

    fun getStation(id: Int) {
        _stationId = id
        val listRef = database.child("stations").child("$id")
        getActualMeasure()
//        listRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val measures = mutableListOf<Measure>()
//                snapshot.child("measures").children.forEach {
//                    val measure = it.getValue(Measure::class.java)
//                    measure?.let { it1 -> measures.add(it1) }
//                }
//                _station.value = Station(snapshot.child("name").value as String, measures)
//                getActualMeasure()
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("QUERYFIREBASE", "$error")
//            }
//        })
    }

    private fun getActualMeasure() {
//        val list = _station.value?.measures
//        _lastMeasure.value = list?.last()
        val listRef = database.child("stations").child("$_stationId")
        val measureList = listRef.child("measures").orderByKey().limitToLast(1)
        measureList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { _lastMeasure.value = it.getValue(Measure::class.java) }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getLastDays() {
        val currentDate = LocalDate.now().toString()
        val listRef = database.child("stations").child("$_stationId")
        val measureList = listRef.child("measures").startAt(currentDate).orderByChild("timestamp").limitToLast(1)
        measureList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { _lastMeasure.value = it.getValue(Measure::class.java) }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    fun fakeValue() {
        val stationPath = database.child("stations").child("1")
        stationPath.child("name").setValue("Extérieur")
        stationPath.child("measures").push().setValue(Measure(System.currentTimeMillis(), -20.0, 22.3, 60.0, 300.0, 4.0, 1.9))
    }

}