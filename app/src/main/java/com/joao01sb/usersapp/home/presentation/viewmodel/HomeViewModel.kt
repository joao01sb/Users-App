package com.joao01sb.usersapp.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import com.joao01sb.usersapp.home.presentation.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val loadAndSyncUsers: LoadAndSyncUsers,
    private val scheduleRemoteSync: ScheduleRemoteSync,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    private var remoteFetchJob: Job? = null


    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            loadAndSyncUsers.invoke().collect {
                handleResult(it)
            }
        }
        syncUsers()
    }

    fun syncUsers() {
        remoteFetchJob?.cancel()
        remoteFetchJob = viewModelScope.launch {
            scheduleRemoteSync.execute(60000L).collect {
                when (it) {
                    is ResultWrapper.Error -> _uiEvents.emit(
                        UiEvent.ShowToast(
                            it.error.message ?: "unknown error"
                        )
                    )

                    is ResultWrapper.Loading -> Unit
                    is ResultWrapper.Success<*> -> {
                        _uiEvents.emit(UiEvent.ShowToast("Users updated"))
                        refresh()
                    }
                }
            }
        }
    }

    fun refresh() {
        remoteFetchJob?.cancel()
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            loadAndSyncUsers.syncUsers().collect {
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: ResultWrapper<List<User>>) {
        when (result) {
            is ResultWrapper.Success -> _uiState.value = UiState(users = result.result)
            is ResultWrapper.Error -> _uiState.value = UiState(isErro = Pair(true, result.error.message ?: "Erro"))
            is ResultWrapper.Loading -> _uiState.value = UiState(isLoading = true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        remoteFetchJob?.cancel()
    }
}