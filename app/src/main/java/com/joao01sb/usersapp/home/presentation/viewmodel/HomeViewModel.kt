package com.joao01sb.usersapp.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocalUseCase
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemoteUseCase
import com.joao01sb.usersapp.home.domain.usecase.SaveUserUseCase
import com.joao01sb.usersapp.home.presentation.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getRemoteUsersUseCase: GetUsersRemoteUseCase,
    private val getLocalUsersUseCase: GetUsersLocalUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    private var remoteFetchJob: Job? = null

    init {
        loadInitialUsers()
    }

    private fun loadInitialUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isErro = null) }
            try {
                getLocalUsersUseCase.invoke().collectLatest { localUsers ->
                    if (localUsers.isNotEmpty()) {
                        _uiState.update {
                            it.copy(isLoading = false, users = localUsers, isErro = null)
                        }
                        scheduleRemoteFetch(delayMillis = 60000L)
                    } else {
                        fetchRemoteUsers(isInitialLoad = true)
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro inesperado ao carregar dados iniciais", e)
                _uiState.update { it.copy(isLoading = false, isErro = Pair(true, "Erro ao carregar dados.")) }
                fetchRemoteUsers(isInitialLoad = true)
            }
        }
    }

    private fun scheduleRemoteFetch(delayMillis: Long = 5000L) {
        remoteFetchJob?.cancel()
        remoteFetchJob = viewModelScope.launch {
            delay(delayMillis)
            fetchRemoteUsers(isInitialLoad = false)
        }
    }

    fun fetchRemoteUsers(isInitialLoad: Boolean, isRetry: Boolean = false) {
        remoteFetchJob?.cancel()
        remoteFetchJob = viewModelScope.launch {
            if (isInitialLoad) {
                _uiState.update { it.copy(isLoading = true, isErro = null) }
            }
            getRemoteUsersUseCase.invoke()
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Error -> {
                            Log.e("HomeViewModel", "Erro da API: ${result.error.message}")
                            handleRemoteFetchError(result.error.message ?: "Erro desconhecido da API", isInitialLoad)
                        }
                        is ResultWrapper.Loading -> {
                            if (isInitialLoad) {
                                _uiState.update { it.copy(isLoading = true) }
                            }
                        }
                        is ResultWrapper.Sucess -> {
                            Log.d("HomeViewModel", "Dados recebidos da API: ${result.result.size} usuários")
                            try {
                                saveUserUseCase.invoke(result.result)
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        users = result.result,
                                        isErro = null
                                    )
                                }
                                if (!isInitialLoad) {
                                    _uiEvents.emit(UiEvent.ShowSnackbar("Dados sincronizados."))
                                }
                            } catch (e: Exception) {
                                Log.e("HomeViewModel", "Erro ao salvar usuários no banco", e)
                                _uiEvents.emit(UiEvent.ShowSnackbar("Erro ao salvar dados localmente."))
                                _uiState.update { it.copy(isLoading = false) }
                            }
                        }
                    }
                }
        }
    }

    private suspend fun handleRemoteFetchError(errorMessage: String, isInitialLoad: Boolean) {
        _uiState.update { currentState ->
            if (isInitialLoad && currentState.users.isEmpty()) {
                currentState.copy(
                    isLoading = false,
                    isErro = Pair(true, "Não foi possível carregar os usuários: $errorMessage")
                )
            } else {
                _uiEvents.emit(UiEvent.ShowSnackbar("Não foi possível sincronizar: $errorMessage"))
                currentState.copy(isLoading = false) // Garante que parou o loading se estava ativo
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        remoteFetchJob?.cancel()
    }


    fun refreshData() {
        remoteFetchJob?.cancel()
        fetchRemoteUsers(isInitialLoad = true, isRetry = true)
    }
}