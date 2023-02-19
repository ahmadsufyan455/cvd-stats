package com.zerodev.covidstats

import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zerodev.covidstats.ui.DashboardActivity
import com.zerodev.covidstats.ui.OnboardActivity
import com.zerodev.covidstats.utils.getConnectivity

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectivity = this.getConnectivity()

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (connectivity != null) {
                if (connectivity.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    if (user != null) {
                        navigateToDashBoard()
                    } else {
                        navigateToOnboard()
                    }
                }
                if (connectivity.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    if (user != null) {
                        navigateToDashBoard()
                    } else {
                        navigateToOnboard()
                    }
                }
            } else {
                navigateToOnboard()
            }
        }, 3000)
    }

    private fun navigateToDashBoard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun navigateToOnboard() {
        startActivity(Intent(this, OnboardActivity::class.java))
        finish()
    }
}