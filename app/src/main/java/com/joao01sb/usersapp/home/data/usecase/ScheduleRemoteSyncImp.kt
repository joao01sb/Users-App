package com.joao01sb.usersapp.home.data.usecase

import android.util.Log
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ScheduleRemoteSyncImp(
    private val loadAndSyncUsers: LoadAndSyncUsers
)  : ScheduleRemoteSync {
    override fun execute(delayMillis: Long): Flow<ResultWrapper<List<User>>> = flow{
        delay(delayMillis)
        emitAll(loadAndSyncUsers.syncUsers())
    }
}