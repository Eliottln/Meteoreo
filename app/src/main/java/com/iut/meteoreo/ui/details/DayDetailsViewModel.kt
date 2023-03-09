package com.iut.meteoreo.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iut.meteoreo.data.Measure

class DayDetailsViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    private var _timestamp: Long = 0
    private var _measureNumber = 0

    private val _daysMeasures = MutableLiveData<ArrayList<Measure>>()
    val daysMeasures: LiveData<ArrayList<Measure>> = _daysMeasures

    fun getDayMeasures(timestamp: Long) {
        _timestamp = timestamp
        reset()
        val query = database
            .child("stations")
            .child("1")
            .child("measures")
            .orderByChild("timestamp")
            .startAt(timestamp.toString())
            .endBefore((timestamp + 86400000).toString())

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tempArray = ArrayList<Measure>()
                dataSnapshot.children.forEach {
                    it.getValue(Measure::class.java)?.let { it1 -> tempArray.add(it1) }
                }
                _daysMeasures.value = tempArray
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun reset() {
        _measureNumber = 0
    }
}