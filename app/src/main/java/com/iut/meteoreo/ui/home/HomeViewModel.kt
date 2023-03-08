package com.iut.meteoreo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.iut.meteoreo.data.DayTemperature
import com.iut.meteoreo.data.Measure
import com.iut.meteoreo.extensions.timestampNextDay
import com.iut.meteoreo.extensions.timestampStartOfDay
import java.time.LocalDate
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    private var _stationId = 0
    private val _daysMeasures = ArrayList<DayTemperature>()
    private val _lastDaysMeasures = MutableLiveData<ArrayList<DayTemperature>>()
    val lastDaysMeasures: LiveData<ArrayList<DayTemperature>> = _lastDaysMeasures
    private val _lastMeasure = MutableLiveData<Measure>()
    val lastMeasure: LiveData<Measure> = _lastMeasure

    fun getStation(id: Int) {
        _stationId = id
        getActualMeasure()
        getLastDays()
    }

    private fun getActualMeasure() {
        val listRef = database.child("stations").child("$_stationId")
        val measureList = listRef.child("measures").orderByKey().limitToLast(1)
        measureList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    _lastMeasure.value = it.getValue(Measure::class.java)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getLastDays() {
        _daysMeasures.clear()
        for (day in 0..6) {
            val date = LocalDate.now().minusDays(day.toLong())
            _daysMeasures.add(DayTemperature(date, Measure(), Measure()))

            val query = database
                .child("stations")
                .child("$_stationId")
                .child("measures")
                .orderByChild("timestamp")
                .startAt(date.timestampStartOfDay().toString())
                .endBefore(date.timestampNextDay().toString())

            query.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var maxTemp = Measure()
                    for (postSnapshot in dataSnapshot.children) {
                        maxTemp = postSnapshot.getValue(Measure::class.java)!!
                    }
                    query.limitToFirst(1)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                var minTemp = Measure()
                                for (postSnapshot in dataSnapshot.children) {
                                    minTemp = postSnapshot.getValue(Measure::class.java)!!
                                }
                                _daysMeasures[day] = DayTemperature(date, maxTemp, minTemp)
                                _lastDaysMeasures.value = ArrayList(_daysMeasures)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun fakeValue() {
        val stationPath = database.child("stations").child("1")
        stationPath.child("name").setValue("Extérieur")
        stationPath.child("measures").push()
            .setValue(Measure(System.currentTimeMillis().toString(), 10.0, 22.3, 60.0, 300.0, 4.0, 1.9))
    }

}