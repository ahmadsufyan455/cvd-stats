package com.zerodev.covidstats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerodev.covidstats.datasource.StatsRepository
import com.zerodev.covidstats.model.Summary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(private val repository: StatsRepository) : ViewModel() {
    private val _summary: MutableLiveData<Summary> = MutableLiveData()
    val summary get() = _summary

    fun setSummary() {
        viewModelScope.launch {
            repository.getSummary().flowOn(Dispatchers.IO).collect {
                _summary.postValue(it)
            }
        }
    }
}