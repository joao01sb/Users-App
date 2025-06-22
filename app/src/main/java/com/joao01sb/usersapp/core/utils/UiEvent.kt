package com.joao01sb.usersapp.core.utils

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}
