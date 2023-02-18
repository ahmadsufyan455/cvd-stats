package com.zerodev.covidstats.network

import com.zerodev.covidstats.model.Country
import com.zerodev.covidstats.model.Summary
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndpoint {
    @GET("countries")
    suspend fun getCountries(): Response<List<Country>>

    @GET("summary")
    suspend fun getSummary(): Response<Summary>
}