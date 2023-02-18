package com.zerodev.covidstats.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Summary(
    @SerializedName("ID") val id: String,
    @SerializedName("Message") val message: String,
    @SerializedName("Global") val global: Global,
    @SerializedName("Countries") val countries: List<Countries>
)

data class Global(
    @SerializedName("NewConfirmed") val newConfirmed: Long,
    @SerializedName("TotalConfirmed") val totalConfirmed: Long,
    @SerializedName("NewDeaths") val newDeaths: Long,
    @SerializedName("TotalDeaths") val totalDeaths: Long,
    @SerializedName("NewRecovered") val newRecovered: Long,
    @SerializedName("TotalRecovered") val totalRecovered: Long,
    @SerializedName("Date") val date: String
)

data class Countries(
    @SerializedName("ID") val id: String,
    @SerializedName("Country") val country: String,
    @SerializedName("Slug") val slug: String,
    @SerializedName("NewConfirmed") val newConfirmed: Long,
    @SerializedName("TotalConfirmed") val totalConfirmed: Long,
    @SerializedName("NewDeaths") val newDeaths: Long,
    @SerializedName("TotalDeaths") val totalDeaths: Long,
    @SerializedName("NewRecovered") val newRecovered: Long,
    @SerializedName("TotalRecovered") val totalRecovered: Long,
    @SerializedName("Date") val date: String
)
