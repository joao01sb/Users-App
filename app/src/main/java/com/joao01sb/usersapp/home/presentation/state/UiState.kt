package com.joao01sb.usersapp.home.presentation.state

import com.joao01sb.usersapp.core.domain.model.User

data class UiState(
    val users: List<User> = emptyList(),
    val isErro: Pair<Boolean, String>? = Pair(false, ""),
    val isLoading: Boolean = false
)
