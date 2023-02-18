package com.zerodev.covidstats.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zerodev.covidstats.R
import com.zerodev.covidstats.databinding.ActivityDashboardBinding
import com.zerodev.covidstats.utils.formatDate
import com.zerodev.covidstats.viewmodel.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityDashboardBinding

    private val viewModel by viewModels<StatsViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        binding.tvName.text = resources.getString(R.string.welcome, currentUser?.displayName)

        viewModel.setSummary()
        viewModel.summary.observe(this) { summary ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                summary.countries.map { it.country }.sorted()
            )
            binding.apply {
                autoComplete.setAdapter(adapter)
                autoComplete.onItemClickListener =
                    OnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position) as String

                        val date = summary.countries.first {
                            it.country.contains(selectedItem)
                        }.date.formatDate()

                        val totalCase = summary.countries.first {
                            it.country.contains(selectedItem)
                        }.totalConfirmed

                        val activeCase = summary.countries.first {
                            it.country.contains(selectedItem)
                        }.newConfirmed

                        val recoveredCase = summary.countries.first {
                            it.country.contains(selectedItem)
                        }.totalRecovered

                        val deathCase = summary.countries.first {
                            it.country.contains(selectedItem)
                        }.totalDeaths

                        tvDate.text = resources.getString(R.string.last_update, date)
                        layoutTotalCase.tvTotalCase.text = totalCase.toString()
                        layoutActiveCase.tvActiveCase.text = activeCase.toString()
                        layoutRecoverCase.tvRecoverCase.text = recoveredCase.toString()
                        layoutDeathCase.tvDeathCase.text = deathCase.toString()
                    }
            }
        }
    }

    private fun signOut() {
        mAuth.signOut()
        startActivity(Intent(this, OnboardActivity::class.java))
        finishAffinity()
    }
}