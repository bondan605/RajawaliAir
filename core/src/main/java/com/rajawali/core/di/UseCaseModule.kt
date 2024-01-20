package com.rajawali.core.di

import com.rajawali.core.domain.usecase.AddRecentSearchUseCase
import com.rajawali.core.domain.usecase.GetAirportsUseCase
import com.rajawali.core.domain.usecase.GetRecentSearchUseCase
import org.koin.dsl.module

val useCaseModule = module {
//    single { AddRecentSearchUseCase(get()) }
    single { GetAirportsUseCase(get()) }
//    single { GetRecentSearchUseCase(get()) }
}
