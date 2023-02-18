package com.zerodev.covidstats.datasource

import com.zerodev.covidstats.model.Summary
import com.zerodev.covidstats.network.ApiEndpoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StatsRepository(private val apiEndpoint: ApiEndpoint) {
    fun getSummary(): Flow<Summary> {
        return flow {
            val response = apiEndpoint.getSummary()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                throw Exception("Error: ${response.code()}")
            }
        }
    }
}