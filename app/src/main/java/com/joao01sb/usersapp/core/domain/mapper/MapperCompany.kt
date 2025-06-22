package com.joao01sb.usersapp.core.domain.mapper

import com.joao01sb.usersapp.core.data.local.entities.CompanyEntity
import com.joao01sb.usersapp.core.data.remote.dto.CompanyDto
import com.joao01sb.usersapp.core.domain.model.Company

fun CompanyEntity.toModel() = Company(
    name = name,
    catchPhrase = catchPhrase,
    bs = bs
)

fun Company.toEntity() = CompanyEntity(
    name = name,
    catchPhrase = catchPhrase,
    bs = bs
)

fun CompanyDto.toModel() = Company(
    name = name,
    catchPhrase = catchPhrase,
    bs = bs
)

