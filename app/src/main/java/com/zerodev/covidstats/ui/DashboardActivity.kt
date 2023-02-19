package com.zerodev.covidstats.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.auth.FirebaseAuth
import com.zerodev.covidstats.R
import com.zerodev.covidstats.databinding.ActivityDashboardBinding
import com.zerodev.covidstats.utils.formatDate
import com.zerodev.covidstats.viewmodel.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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

        binding.apply {
            tvName.text = resources.getString(R.string.welcome, currentUser?.displayName)
            llLang.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            autoComplete.setText(resources.getString(R.string.select_country))
        }

        viewModel.setSummary()
        viewModel.summary.observe(this) { summary ->
            if (summary.countries != null) {
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
                            val summaryData = summary.countries.first {
                                it.country.contains(selectedItem)
                            }

                            val date = summaryData.date.formatDate()
                            val totalCase = summaryData.totalConfirmed
                            val activeCase = summaryData.newConfirmed
                            val recoveredCase = summaryData.totalRecovered
                            val deathCase = summaryData.totalDeaths

                            tvDate.text = resources.getString(R.string.last_update, date)
                            layoutTotalCase.tvTotalCase.text = totalCase.toString()
                            layoutActiveCase.tvActiveCase.text = activeCase.toString()
                            layoutRecoverCase.tvRecoverCase.text = recoveredCase.toString()
                            layoutDeathCase.tvDeathCase.text = deathCase.toString()
                            tvAffected.text = activeCase.toString()
                            tvRecovered.text = recoveredCase.toString()

                            pieChart.apply {
                                setUsePercentValues(true)
                                description.isEnabled = false
                                setExtraOffsets(5f, 10f, 5f, 5f)
                                dragDecelerationFrictionCoef = 0.95f
                                isDrawHoleEnabled = true
                                setHoleColor(Color.WHITE)
                                setTransparentCircleColor(Color.WHITE)
                                setTransparentCircleAlpha(110)
                                holeRadius = 58f
                                transparentCircleRadius = 61f
                                setDrawCenterText(true)
                                rotationAngle = 0f
                                isRotationEnabled = true
                                isHighlightPerTapEnabled = true
                                animateY(1400, Easing.EaseInOutQuad)
                                legend.isEnabled = false
                                setEntryLabelColor(Color.WHITE)
                                setEntryLabelTextSize(12f)

                                val entries: ArrayList<PieEntry> = ArrayList()
                                entries.add(PieEntry(activeCase.toFloat()))
                                entries.add(PieEntry(recoveredCase.toFloat()))

                                val dataSet = PieDataSet(entries, "Covid Stats")

                                dataSet.setDrawIcons(false)

                                dataSet.sliceSpace = 3f
                                dataSet.iconsOffset = MPPointF(0f, 40f)
                                dataSet.selectionShift = 5f

                                val colors: ArrayList<Int> = ArrayList()
                                colors.add(
                                    ContextCompat.getColor(
                                        applicationContext,
                                        R.color.green
                                    )
                                )
                                colors.add(
                                    ContextCompat.getColor(
                                        applicationContext,
                                        R.color.aqua
                                    )
                                )
                                colors.add(ContextCompat.getColor(applicationContext, R.color.grey))

                                dataSet.colors = colors

                                val pieData = PieData(dataSet).apply {
                                    setValueFormatter(PercentFormatter())
                                    setValueTextSize(8f)
                                    setValueTypeface(Typeface.DEFAULT_BOLD)
                                    setValueTextColor(Color.WHITE)
                                }

                                data = pieData
                                pieChart.highlightValues(null)
                                pieChart.invalidate()
                            }
                        }
                }
            }
        }

        binding.btnLogout.setOnClickListener { signOut() }
    }

    private fun signOut() {
        mAuth.signOut()
        startActivity(Intent(this, OnboardActivity::class.java))
        finishAffinity()
    }
}