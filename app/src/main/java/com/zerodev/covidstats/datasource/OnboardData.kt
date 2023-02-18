package com.zerodev.covidstats.datasource

import com.zerodev.covidstats.R
import com.zerodev.covidstats.model.Onboard

object OnboardData {
    fun onboards(): List<Onboard> {
        return listOf(
            Onboard(
                image = R.drawable.ob1,
                title = "Wear a Mask",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Volutpat felis sit eget euismod et vulputate. Vitae lacus, maecenas odio ac."
            ),
            Onboard(
                image = R.drawable.ob2,
                title = "Hand Wash & Sanitize",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Volutpat felis sit eget euismod et vulputate. Vitae lacus, maecenas odio ac."
            ),
            Onboard(
                image = R.drawable.ob3,
                title = "Pyshical Distancing",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Volutpat felis sit eget euismod et vulputate. Vitae lacus, maecenas odio ac."
            )
        )
    }
}