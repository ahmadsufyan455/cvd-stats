package com.zerodev.covidstats

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zerodev.covidstats.ui.DashboardActivity
import com.zerodev.covidstats.ui.OnboardActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, OnboardActivity::class.java))
                finish()
            }
        }, 3000)
    }
}