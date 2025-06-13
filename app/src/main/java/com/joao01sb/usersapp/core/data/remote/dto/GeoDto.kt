package com.joao01sb.usersapp.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeoDto(
    val lat: String,
    val lng: String
)