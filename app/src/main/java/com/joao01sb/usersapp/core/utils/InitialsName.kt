package com.joao01sb.usersapp.core.utils

fun getInitials(fullName: String): String {
    return fullName
        .trim()
        .split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercase() }
        .joinToString("")
        .ifEmpty { "?" }
}