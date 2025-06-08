package com.joao01sb.usersapp.core.data.local.mapper

import com.joao01sb.usersapp.core.data.local.entities.AddressEmbedded
import com.joao01sb.usersapp.core.domain.model.Address

fun AddressEmbedded.toModel() = Address(
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    geo = geo.toModel()
)

fun Address.toEntity() = AddressEmbedded(
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    geo = geo.toEntity()
)