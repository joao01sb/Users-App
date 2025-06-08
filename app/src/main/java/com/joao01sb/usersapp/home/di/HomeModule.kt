package com.joao01sb.usersapp.home.di

import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.home.data.datasource.UserLocalDataSourceImp
import com.joao01sb.usersapp.home.data.datasource.UserRemoteDataSourceImp
import com.joao01sb.usersapp.home.data.repository.UserLocalRepositoryImp
import com.joao01sb.usersapp.home.data.repository.UserRemoteRepositoryImp
import com.joao01sb.usersapp.home.data.usecase.GetUsersLocalUseCaseImp
import com.joao01sb.usersapp.home.data.usecase.SaveUserUseCaseImp
import com.joao01sb.usersapp.home.data.usecase.GetUsersRemoteUseCaseImp
import com.joao01sb.usersapp.home.domain.datasource.UserLocalDataSource
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocalUseCase
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemoteUseCase
import com.joao01sb.usersapp.home.domain.usecase.SaveUserUseCase
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
    single<GetUsersLocalUseCase> {
        GetUsersLocalUseCaseImp(get<UserLocalRepository>())
    }
    single<SaveUserUseCase> {
        SaveUserUseCaseImp(get<UserLocalRepository>())
    }
    single<UserRemoteDataSource> {
        UserRemoteDataSourceImp(get<ApiService>())
    }
    single<UserRemoteRepository> {
        UserRemoteRepositoryImp(get<UserRemoteDataSource>())
    }
    single<GetUsersRemoteUseCase> {
        GetUsersRemoteUseCaseImp(get<UserRemoteRepository>())
    }
    viewModel {
        HomeViewModel(
            get<GetUsersRemoteUseCase>(),
            get<GetUsersLocalUseCase>(),
            get<SaveUserUseCase>()
        )
    }
}