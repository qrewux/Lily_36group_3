package com.example.appaboutplants.models

import com.google.gson.annotations.SerializedName

data class PlantListResponse(
    @SerializedName("data")
    val data: List<Plant>
)
