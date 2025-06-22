package com.joao01sb.usersapp.home.di

import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.home.data.datasource.UserLocalDataSourceImp
import com.joao01sb.usersapp.home.data.datasource.UserRemoteDataSourceImp
import com.joao01sb.usersapp.home.data.repository.UserLocalRepositoryImp
import com.joao01sb.usersapp.home.data.repository.UserRemoteRepositoryImp
import com.joao01sb.usersapp.home.data.usecase.GetUsersLocalImp
import com.joao01sb.usersapp.home.data.usecase.SaveUsersImp
import com.joao01sb.usersapp.home.data.usecase.GetUsersRemoteImp
import com.joao01sb.usersapp.home.data.usecase.LoadAndSyncUsersImp
import com.joao01sb.usersapp.home.data.usecase.ScheduleRemoteSyncImp
import com.joao01sb.usersapp.home.domain.datasource.UserLocalDataSource
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocal
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemote
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.SaveUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<UserLocalDataSource> {
        UserLocalDataSourceImp(get<UserDao>())
    }
    single<UserLocalRepository> {
        UserLocalRepositoryImp(get<UserLocalDataSource>())
    }
    single<GetUsersLocal> {
        GetUsersLocalImp(get<UserLocalRepository>())
    }
    single<SaveUsers> {
        SaveUsersImp(get<UserLocalRepository>())
    }
    single<UserRemoteDataSource> {
        UserRemoteDataSourceImp(get<ApiService>())
    }
    single<UserRemoteRepository> {
        UserRemoteRepositoryImp(get<UserRemoteDataSource>())
    }
    single<GetUsersRemote> {
        GetUsersRemoteImp(get<UserRemoteRepository>())
    }
    single<LoadAndSyncUsers> {
        LoadAndSyncUsersImp(
            get<GetUsersRemote>(),
            get<GetUsersLocal>(),
            get<SaveUsers>()
        )
    }
    single<ScheduleRemoteSync> {
        ScheduleRemoteSyncImp(get<LoadAndSyncUsers>(),)
    }
    viewModel {
        HomeViewModel(
            get<LoadAndSyncUsers>(),
            get<ScheduleRemoteSync>()
        )
    }
}
