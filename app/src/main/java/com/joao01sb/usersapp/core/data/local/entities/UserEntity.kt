package com.joao01sb.usersapp.core.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "_users"
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded(prefix = "_address") val address: AddressEmbedded,
    val phone: String,
    val website: String,
    @Embedded(prefix = "_company") val company: CompanyEmbedded
)