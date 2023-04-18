package com.example.appaboutplants.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appaboutplants.domain.PlantRepository
import com.example.appaboutplants.data.models.Plant
import kotlinx.coroutines.launch

class PlantListViewModel(private val plantRepository: PlantRepository) : ViewModel() {
    private val _latestPlant = MutableLiveData<List<Plant>>()
    val latestPlant: LiveData<List<Plant>> = _latestPlant

    fun fetchLatestPLants() {
        viewModelScope.launch {
            val plants = plantRepository.getAllPlants()
            _latestPlant.postValue(plants)
        }
    }
}