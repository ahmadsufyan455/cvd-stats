package com.zerodev.covidstats.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("Country") val country: String,
    @SerializedName("Slug") val slug: String,
    @SerializedName("ISO2") val iso2: String
)
