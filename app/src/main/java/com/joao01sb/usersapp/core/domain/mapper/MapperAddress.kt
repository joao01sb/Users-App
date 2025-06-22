package com.joao01sb.usersapp.core.domain.mapper

import com.joao01sb.usersapp.core.data.local.entities.AddressEntity
import com.joao01sb.usersapp.core.data.remote.dto.AddressDto
import com.joao01sb.usersapp.core.domain.model.Address

fun AddressEntity.toModel() = Address(
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    geo = geo.toModel()
)

fun Address.toEntity() = AddressEntity(
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    geo = geo.toEntity()
)

fun AddressDto.toModel() = Address(
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    geo = geo.toModel()
)
