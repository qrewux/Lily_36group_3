package com.example.appaboutplants.data.models

import com.google.gson.annotations.SerializedName

data class PlantListResponse(
    @SerializedName("data")
    val data: List<Plant>
)
