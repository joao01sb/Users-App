package com.joao01sb.usersapp.home.presentation.fake

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.core.utils.TIME_FOR_SYNC_USERS
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.presentation.fake.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.home.presentation.state.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FakeHomeViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun clearViewModel() {
        _uiState.update {
            UiState()
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        _uiState.update {
            it.copy(
                isLoading = false,
                users = MockUserFactory.createUserListEntity().toModelForEntenty()
            )
        }
    }

    fun loadUsers() {
        _uiState.update {
            it.copy(isLoading = true, isErro = Pair(false, ""))
        }
        _uiState.update {
            it.copy(
                isLoading = false,
                users = MockUserFactory.createUserListEntity().toModelForEntenty()
            )
        }
    }

    fun loadUsersError() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        _uiState.update {
            it.copy(
                isLoading = false,
                isErro = Pair(true, "Error loading users"),
                users = emptyList()
            )
        }
    }

    fun syncUsers() {
        viewModelScope.launch {
            delay(5000L)
            _uiEvents.emit(UiEvent.ShowToast("Users updated"))
            _uiState.update {
                it.copy(
                    isLoading = false,
                    users = MockUserFactory.createUserListEntity().toModelForEntenty()
                )
            }
        }
    }

    fun syncUsersError() {
        viewModelScope.launch {
            _uiEvents.emit(UiEvent.ShowToast("Error syncing users"))
        }
    }

    fun resetState() {
        _uiState.value = UiState()
    }
}