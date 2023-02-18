package com.zerodev.covidstats.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.zerodev.covidstats.R
import com.zerodev.covidstats.databinding.ActivityOnboardBinding
import com.zerodev.covidstats.datasource.OnboardData
import com.zerodev.covidstats.ui.adapter.OnboardAdapter

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding
    private lateinit var onboardAdapter: OnboardAdapter
    private lateinit var indicators: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnboard()
        setupOnboardIndicator()
        setCurrentIndicator(0)

        binding.vpOnboard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.btnNext.setOnClickListener {
            binding.apply {
                if (vpOnboard.currentItem + 1 < onboardAdapter.itemCount) {
                    vpOnboard.currentItem = vpOnboard.currentItem + 1
                } else {
                    Toast.makeText(this@OnboardActivity, "End page", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupOnboard() {
        val data = OnboardData.onboards()
        onboardAdapter = OnboardAdapter(data)
        binding.vpOnboard.adapter = onboardAdapter
    }

    private fun setupOnboardIndicator() {
        val indicatorsCount = onboardAdapter.itemCount
        indicators = Array(indicatorsCount) { ImageView(this) }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)

        for (i in 0 until indicatorsCount) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.onboard_indicator_inactive
                )
            )
            indicators[i].layoutParams = params
            binding.llOnboardIndicator.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.llOnboardIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.llOnboardIndicator.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboard_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboard_indicator_inactive
                    )
                )
            }
        }

        binding.apply {
            if (index == onboardAdapter.itemCount - 1) {
                btnNext.text = resources.getString(R.string.login_gmail)
                val icon = R.drawable.google_sign
                btnNext.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            } else {
                btnNext.text = resources.getString(R.string.next)
                btnNext.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }
    }
}