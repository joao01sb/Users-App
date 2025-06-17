package com.joao01sb.usersapp.details.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.details.domain.usecase.GetUserById
import com.joao01sb.usersapp.details.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getUserById: GetUserById,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userId: Int = checkNotNull(savedStateHandle.get<Int>("userId"))

    private val _stateDetails = MutableStateFlow<UiState>(UiState())
    val stateDetails = _stateDetails.asStateFlow()


    init {
        getUser()
    }

    fun getUser() {
        _stateDetails.update {
            it.copy(result = ResultWrapper.Loading)
        }
        viewModelScope.launch {
            getUserById(userId).onSuccess { user ->
                _stateDetails.update {
                    it.copy(result = ResultWrapper.Success(user))
                }
            }.onFailure { error ->
                _stateDetails.update {
                    it.copy(result = ResultWrapper.Error(error))
                }
            }
        }
    }


}