package com.joao01sb.usersapp.core.data.local.entities

import androidx.room.Embedded

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded(prefix = "geo_") val geo: GeoEntity
)