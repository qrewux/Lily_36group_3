package com.example.appaboutplants.data.models

import com.google.gson.annotations.SerializedName

data class PlantDetailsResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("common_name")
    val commonName: String,
    @SerializedName("scientific_name")
    val scientificName: List<String>,
    @SerializedName("default_image")
    val default_image: DefaultImage,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("cycle")
    val cycle: String,
    @SerializedName("watering")
    val watering: String,
    @SerializedName("description")
    val description: String
)

data class DefaultImage(
    val license: Int,
    val license_name: String,
    val license_url: String,
    val original_url: String,
    val regular_url: String,
    val medium_url: String,
    val small_url: String,
    val thumbnail: String
)