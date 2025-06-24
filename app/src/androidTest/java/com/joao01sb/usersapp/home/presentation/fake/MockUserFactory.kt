package com.joao01sb.usersapp.home.presentation.fake

import com.joao01sb.usersapp.core.data.local.entities.AddressEntity
import com.joao01sb.usersapp.core.data.local.entities.CompanyEntity
import com.joao01sb.usersapp.core.data.local.entities.GeoEntity
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.data.remote.dto.AddressDto
import com.joao01sb.usersapp.core.data.remote.dto.CompanyDto
import com.joao01sb.usersapp.core.data.remote.dto.GeoDto
import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.domain.mapper.toModel

object MockUserFactory {

    fun createUserEntity(id: Int = 1) = UserEntity(
        id = id,
        name = "João Silva",
        username = "joaosilva",
        email = "joao.silva@example.com",
        address = AddressEntity(
            street = "Rua das Flores",
            suite = "Apto 123",
            city = "Curitiba",
            zipcode = "80010-000",
            geo = GeoEntity(
                lat = "156",
                lng = "154"
            )
        ),
        phone = "(41) 99999-8888",
        website = "http://joaosilva.com",
        company = CompanyEntity(
            name = "Silva & Cia",
            catchPhrase = "Negócios que transformam",
            bs = "tecnologia, inovação, parceria"
        )
    )

    fun createUserDto(id: Int = 1) = UserDto(
        id = id,
        name = "João Silva",
        username = "joaosilva",
        email = "joao.silva@example.com",
        address = AddressDto(
            street = "Rua das Flores",
            suite = "Apto 123",
            city = "Curitiba",
            zipcode = "80010-000",
            geo = GeoDto(
                lat = "156",
                lng = "154"
            )
        ),
        phone = "(41) 99999-8888",
        website = "http://joaosilva.com",
        company = CompanyDto(
            name = "Silva & Cia",
            catchPhrase = "Negócios que transformam",
            bs = "tecnologia, inovação, parceria"
        )
    )

    fun createUserListEntity() = listOf(
        createUserEntity(id = 1),
        createUserEntity(id = 2)
    )

    fun createUserListDto() = listOf(
        createUserDto(id = 1),
        createUserDto(id = 2)
    )

    fun List<UserEntity>.toModelForEntenty() = this.map { it.toModel() }
    fun List<UserDto>.toModelForDto() = this.map { it.toModel() }
}