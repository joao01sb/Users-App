package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocal
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemote
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.SaveUsers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class LoadAndSyncUsersImp(
    private val getRemoteUsers: GetUsersRemote,
    private val getLocalUsers: GetUsersLocal,
    private val saveUsers: SaveUsers
) : LoadAndSyncUsers {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun invoke(): Flow<ResultWrapper<List<User>>> {
        return getLocalUsers.invoke()
            .flatMapLatest { localUsers ->
                if (localUsers.isNotEmpty()) {
                    flowOf(ResultWrapper.Success(localUsers))
                } else {
                    syncUsers()
                }
            }.catch { e ->
                emit(ResultWrapper.Error(e))
            }
    }



    override fun syncUsers(): Flow<ResultWrapper<List<User>>> = flow {
        getRemoteUsers.invoke().collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    saveUsers.invoke (result.result)
                    emit(ResultWrapper.Success(result.result))
                }
                is ResultWrapper.Error -> emit(result)
                is ResultWrapper.Loading -> Unit
            }
        }
    }

}


