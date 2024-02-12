package com.rajawali.core.di

import com.rajawali.core.domain.usecase.CreatePassengerInformationInputUseCase
import com.rajawali.core.domain.usecase.CreateReservationUseCase
import com.rajawali.core.domain.usecase.GetAirportsUseCase
import com.rajawali.core.domain.usecase.GetAvailableSeatsUseCase
import com.rajawali.core.domain.usecase.GetFlightByDate
import com.rajawali.core.domain.usecase.GetFlightsUseCase
import com.rajawali.core.domain.usecase.GetMealsUseCase
import com.rajawali.core.domain.usecase.GetPreferredFlight
import com.rajawali.core.domain.usecase.GetReservationByIdUseCase
import com.rajawali.core.domain.usecase.PayReservationUseCase
import org.koin.dsl.module

val useCaseModule = module {
//    single { AddRecentSearchUseCase(get()) }
    single { GetAirportsUseCase(get()) }
//    single { GetRecentSearchUseCase(get()) }
    single { GetFlightsUseCase(get()) }
    single { GetPreferredFlight(get()) }
    single { GetFlightByDate() }
    single { CreatePassengerInformationInputUseCase() }
    single { GetMealsUseCase(get()) }
    single { CreateReservationUseCase(get()) }
    single { GetAvailableSeatsUseCase(get()) }
    single { PayReservationUseCase(get()) }
    single { GetReservationByIdUseCase(get()) }
}
