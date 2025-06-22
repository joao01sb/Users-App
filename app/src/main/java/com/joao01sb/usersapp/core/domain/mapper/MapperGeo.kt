package com.joao01sb.usersapp.core.domain.mapper

import com.joao01sb.usersapp.core.data.local.entities.GeoEntity
import com.joao01sb.usersapp.core.data.remote.dto.GeoDto
import com.joao01sb.usersapp.core.domain.model.Geo

fun GeoEntity.toModel() = Geo(
    lat = lat,
    lng = lng
)

fun Geo.toEntity() = GeoEntity(
    lat = lat,
    lng = lng
)

fun GeoDto.toModel() = Geo(
    lat = lat,
    lng = lng
)
