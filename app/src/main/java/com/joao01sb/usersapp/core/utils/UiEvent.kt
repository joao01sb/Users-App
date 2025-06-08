package com.joao01sb.usersapp.core.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}