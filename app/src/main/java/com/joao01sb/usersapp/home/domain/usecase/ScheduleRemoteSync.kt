package com.joao01sb.usersapp.home.domain.usecase

import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ScheduleRemoteSync {

    fun execute(delayMillis: Long = 5000L) : Flow<ResultWrapper<List<User>>>

}