package com.example.appaboutplants.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appaboutplants.domain.PlantRepository
import com.example.appaboutplants.models.DetailPlant

class PlantDetailViewModel(private val plantRepository: PlantRepository) : ViewModel() {
    private val _plant = MutableLiveData<DetailPlant>()
    val plant: LiveData<DetailPlant> = _plant

    suspend fun fetchPlantById(id: Int) {
        try {
            val response = plantRepository.getPlantById(id)
            if (response.isSuccessful) {
                val plant = response.body()
                val concatenatedNames = plant?.scientificName?.joinToString(", ")
                _plant.value = DetailPlant(plant?.id ?: 0, plant?.commonName ?: "", concatenatedNames ?: "", plant?.default_image?.original_url, plant?.description, plant?.type, plant?.dimension, plant?.cycle, plant?.watering)
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
