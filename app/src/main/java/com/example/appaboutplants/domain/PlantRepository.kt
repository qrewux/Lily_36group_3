package com.example.appaboutplants.domain

import com.example.appaboutplants.data.models.Plant
import com.example.appaboutplants.data.models.PlantApiService
import com.example.appaboutplants.data.models.PlantDetailsResponse
import retrofit2.Response

class PlantRepository(private val plantApiService: PlantApiService) {

    suspend fun getAllPlants(): List<Plant> {
        return plantApiService.getAllPlants().data
    }

    suspend fun getPlantById(id: Int): Response<PlantDetailsResponse> {
        return plantApiService.getPlantById(id)
    }

}