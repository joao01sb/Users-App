package com.joao01sb.usersapp.core.data.local.mapper

import com.joao01sb.usersapp.core.data.local.entities.CompanyEmbedded
import com.joao01sb.usersapp.core.domain.model.Company

fun CompanyEmbedded.toModel() = Company(
    name = name,
    catchPhrase = catchPhrase,
    bs = bs
)

fun Company.toEntity() = CompanyEmbedded(
    name = name,
    catchPhrase = catchPhrase,
    bs = bs
)