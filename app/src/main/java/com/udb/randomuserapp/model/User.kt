package com.udb.randomuserapp.model

import com.google.gson.annotations.SerializedName

// Modelo principal para la respuesta de la API
data class RandomUserResponse(
    @SerializedName("results")
    val results: List<User>,
    @SerializedName("info")
    val info: Info
)

// Modelo para cada usuario
data class User(
    @SerializedName("name")
    val name: Name,
    @SerializedName("email")
    val email: String,
    @SerializedName("picture")
    val picture: Picture,
    @SerializedName("location")
    val location: Location
)

// Modelo para el nombre
data class Name(
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
) {
    // Función para obtener el nombre completo
    fun getFullName(): String {
        return "$title $first $last"
    }
}

// Modelo para las imágenes
data class Picture(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)

// Modelo para la ubicación
data class Location(
    @SerializedName("street")
    val street: Street,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("country")
    val country: String
) {
    fun getFullAddress(): String {
        return "$city, $state, $country"
    }
}

data class Street(
    @SerializedName("number")
    val number: Int,
    @SerializedName("name")
    val name: String
)

// Modelo para info adicional
data class Info(
    @SerializedName("seed")
    val seed: String,
    @SerializedName("results")
    val results: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("version")
    val version: String
)