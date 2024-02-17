package com.rajawali.core.di

import com.rajawali.core.data.local.LocalRepositoryImp
import com.rajawali.core.data.remote.RemoteRepositoryImp
import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.domain.repository.RemoteRepository
import org.koin.dsl.module

val localRepository = module {
    single<LocalRepository> { LocalRepositoryImp(get(), get()) }
}

val remoteRepository = module {
    single<RemoteRepository> { RemoteRepositoryImp(get()) }
}