package com.example.appaboutplants.domain

import com.example.appaboutplants.data.models.Plant
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class PlantDeserializer : JsonDeserializer<Plant> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Plant {

        print(json)

        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON")

        val id = jsonObject.get("id")?.asInt ?: 0
        val commonName = jsonObject.get("common_name")?.asString ?: ""
        val scientificName = jsonObject.get("scientific_name")?.asJsonArray?.get(0)?.asString ?: ""
        val coverImageUrl = jsonObject.get("default_image")?.asJsonObject?.get("original_url")?.asString ?: ""

        return Plant(id, commonName, scientificName, coverImageUrl)
    }
}
