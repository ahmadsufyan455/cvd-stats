package com.zerodev.covidstats.datasource

import android.content.Context
import com.zerodev.covidstats.R
import com.zerodev.covidstats.model.Onboard

object OnboardData {
    fun onboards(context: Context): List<Onboard> {
        return listOf(
            Onboard(
                image = R.drawable.ob1,
                title = context.getString(R.string.wear_a_mask),
                description = context.getString(R.string.dummy_content)
            ),
            Onboard(
                image = R.drawable.ob2,
                title = context.getString(R.string.hand_wash),
                description = context.getString(R.string.dummy_content)
            ),
            Onboard(
                image = R.drawable.ob3,
                title = context.getString(R.string.physical_distance),
                description = context.getString(R.string.dummy_content)
            )
        )
    }
}