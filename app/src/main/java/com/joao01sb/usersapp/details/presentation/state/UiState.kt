package com.joao01sb.usersapp.details.presentation.state

import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper

data class UiState(
    val result: ResultWrapper<User> = ResultWrapper.Loading,
)

