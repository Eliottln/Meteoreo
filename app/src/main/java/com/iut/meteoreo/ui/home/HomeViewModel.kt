package com.iut.meteoreo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.iut.meteoreo.data.DayTemperature
import com.iut.meteoreo.data.Measure
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
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

    fun getLastDays() {
        _daysMeasures.clear()
        for (day in 0..6) {
            val date = LocalDate.now().minusDays(day.toLong())
            _daysMeasures.add(DayTemperature(date.dayOfWeek.toString(), 0.0, 0.0))
            val timestamp = date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            val listRef = database.child("stations").child("$_stationId").child("measures")
            val query = listRef.orderByChild("timestamp").startAt(timestamp.toString()).endBefore((timestamp + 86400000).toString())
            query.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QUERYFIREBASE", "$dataSnapshot")
                    var maxTemp = 0.0
                    for (postSnapshot in dataSnapshot.children) {
                        val measure: Measure = postSnapshot.getValue(Measure::class.java)!!
                        maxTemp = measure.temperature!!
                    }
                    query.limitToFirst(1)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                var minTemp = 0.0
                                for (postSnapshot in dataSnapshot.children) {
                                    val measure: Measure =
                                        postSnapshot.getValue(Measure::class.java)!!
                                    minTemp = measure.temperature!!
                                }
                                val formatter = DateTimeFormatter.ofPattern("EEE", Locale("fr"))
                                _daysMeasures[day] = DayTemperature(date.format(formatter), maxTemp, minTemp)
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