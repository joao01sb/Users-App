package com.joao01sb.usersapp.core.data.local.mapper

import com.joao01sb.usersapp.core.data.local.entities.GeoEmbedded
import com.joao01sb.usersapp.core.domain.model.Geo

fun GeoEmbedded.toModel() = Geo(
    lat = lat,
    lng = lng
)

fun Geo.toEntity() = GeoEmbedded(
    lat = lat,
    lng = lng
)