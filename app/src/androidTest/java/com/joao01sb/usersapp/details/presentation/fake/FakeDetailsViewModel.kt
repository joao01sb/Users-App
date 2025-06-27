package com.joao01sb.usersapp.details.presentation.fake

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.details.presentation.state.UiState
import com.joao01sb.usersapp.home.presentation.fake.MockUserFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FakeDetailsViewModel : ViewModel() {

    private val _stateDetails = MutableStateFlow<UiState>(UiState())
    val stateDetails = _stateDetails.asStateFlow()


    fun getUser() {
        _stateDetails.update {
            it.copy(result = ResultWrapper.Loading)
        }

        val user = MockUserFactory.createUserEntity().toModel()

        _stateDetails.update {
            it.copy(result = ResultWrapper.Success(user))
        }
    }

    fun getUserLoading() {
        _stateDetails.update {
            it.copy(result = ResultWrapper.Loading)
        }
    }

    fun getUserError() {
        _stateDetails.update {
            it.copy(result = ResultWrapper.Loading)
        }

        _stateDetails.update {
            it.copy(result = ResultWrapper.Error(Exception("Error loading user")))
        }
    }

    fun clearViewModel() {
        _stateDetails.update {
            UiState()
        }
    }


}