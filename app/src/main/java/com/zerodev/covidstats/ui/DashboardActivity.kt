package com.zerodev.covidstats.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zerodev.covidstats.databinding.ActivityDashboardBinding
import com.zerodev.covidstats.viewmodel.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityDashboardBinding

    private val viewModel by viewModels<StatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        viewModel.setSummary()
        viewModel.summary.observe(this) {
            binding.tvName.text = it.countries[0].country
        }
    }

    private fun signOut() {
        mAuth.signOut()
        startActivity(Intent(this, OnboardActivity::class.java))
        finishAffinity()
    }
}