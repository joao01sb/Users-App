package com.joao01sb.usersapp.details.di

import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.details.data.datasource.LocalDataSourceImp
import com.joao01sb.usersapp.details.data.repository.UserDetailsRepositoryImp
import com.joao01sb.usersapp.details.data.usecase.GetUserByIdImp
import com.joao01sb.usersapp.details.domain.datasource.LocalDataSource
import com.joao01sb.usersapp.details.domain.repository.UserDetailsRepository
import com.joao01sb.usersapp.details.domain.usecase.GetUserById
import org.koin.dsl.module

val detailsModule = module {

    single<LocalDataSource> {
        LocalDataSourceImp(dao = get<UserDao>())
    }
    single<UserDetailsRepository> {
        UserDetailsRepositoryImp(datasource = get<LocalDataSource>())
    }
    single<GetUserById> {
        GetUserByIdImp(repository = get<UserDetailsRepository>())
    }


}