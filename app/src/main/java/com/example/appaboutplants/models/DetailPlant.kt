package com.example.appaboutplants.models

data class DetailPlant (
    var id: Int,
    var commonName: String,
    var scientificName: String,
    var coverImageUrl: String?,
    var description: String?,
    var type: String?,
    var dimension: String?,
    var cycle: String?,
    var watering: String?
)
