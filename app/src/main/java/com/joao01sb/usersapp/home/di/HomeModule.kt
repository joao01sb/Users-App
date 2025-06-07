package com.joao01sb.usersapp.home.di

import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.home.data.datasource.UserRemoteDataSourceImp
import com.joao01sb.usersapp.home.data.repository.UserRepositoryImp
import com.joao01sb.usersapp.home.data.usecase.UserUseCaseImp
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import com.joao01sb.usersapp.home.domain.repository.UserRepository
import com.joao01sb.usersapp.home.domain.usecase.UserUseCase
import com.joao01sb.usersapp.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<UserRemoteDataSource> {
        UserRemoteDataSourceImp(get<ApiService>())
    }
    single<UserRepository> {
        UserRepositoryImp(get<UserRemoteDataSource>())
    }
    single<UserUseCase> {
        UserUseCaseImp(get<UserRepository>())
    }
    viewModel { HomeViewModel(get<UserUseCase>()) }
}