package com.rajawali.core.di

import com.rajawali.core.domain.usecase.GetAirportsUseCase
import com.rajawali.core.domain.usecase.GetFlightByDate
import com.rajawali.core.domain.usecase.GetFlightsUseCase
import com.rajawali.core.domain.usecase.GetPreferredFlight
import org.koin.dsl.module

val useCaseModule = module {
//    single { AddRecentSearchUseCase(get()) }
    single { GetAirportsUseCase(get()) }
//    single { GetRecentSearchUseCase(get()) }
    single { GetFlightsUseCase(get()) }
    single { GetPreferredFlight(get()) }
    single { GetFlightByDate() }
}
