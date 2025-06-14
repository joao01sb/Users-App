package com.joao01sb.usersapp.core.domain.mapper

import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.domain.model.User

fun UserEntity.toModel() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    address = address.toModel(),
    phone = phone,
    website = website,
    company = company.toModel()
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    username = username,
    email = email,
    address = address.toEntity(),
    phone = phone,
    website = website,
    company = company.toEntity()
)

fun UserDto.toModel() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    address = address.toModel(),
    phone = phone,
    website = website,
    company = company.toModel()
)