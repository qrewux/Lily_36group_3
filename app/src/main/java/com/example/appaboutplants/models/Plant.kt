package com.example.appaboutplants.models

data class Plant(
    var id: Int,
    var commonName: String,
    var scientificName: String,
    var coverImageUrl: String?,
)