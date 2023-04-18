package com.example.appaboutplants.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantApiService {
    companion object {
        const val API_KEY = "sk-PR4I643cda45898cd544"
    }

    @GET("species-list")
    suspend fun getAllPlants(@Query("page") page: Int = 1, @Query("key") key: String = API_KEY): PlantListResponse


    @GET("species/details/{id}")
    suspend fun getPlantById(@Path("id") id: Int, @Query("key") apiKey: String = API_KEY): Response<PlantDetailsResponse>

}