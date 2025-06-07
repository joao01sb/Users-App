package com.joao01sb.usersapp.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.home.domain.usecase.UserUseCase
import com.joao01sb.usersapp.home.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(value = UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            userUseCase.invoke().collect {
                when(it) {
                    is ResultApiService.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isErro = Pair(true, it.error.message ?: "Unknow error")
                            )
                        }
                    }
                    is ResultApiService.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                    is ResultApiService.Sucess -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isErro = null,
                                users = it.result
                            )
                        }
                    }
                }
            }
        }
    }

}