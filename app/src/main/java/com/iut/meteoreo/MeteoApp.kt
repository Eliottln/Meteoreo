package com.iut.meteoreo

import android.app.Application
import com.google.firebase.FirebaseApp

class MeteoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialiser Firebase
        FirebaseApp.initializeApp(this)
    }

}