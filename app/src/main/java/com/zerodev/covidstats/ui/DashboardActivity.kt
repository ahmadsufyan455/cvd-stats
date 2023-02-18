package com.zerodev.covidstats.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zerodev.covidstats.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        binding.tvName.text = currentUser?.displayName
        binding.tvName.setOnClickListener { signOut() }
    }

    private fun signOut() {
        mAuth.signOut()
        startActivity(Intent(this, OnboardActivity::class.java))
        finishAffinity()
    }
}