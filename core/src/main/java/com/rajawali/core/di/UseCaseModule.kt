package com.rajawali.core.di

import com.rajawali.core.domain.usecase.AddRecentSearchUseCase
import com.rajawali.core.domain.usecase.ClearRecentSearchUseCase
import com.rajawali.core.domain.usecase.CreatePassengerInformationInputUseCase
import com.rajawali.core.domain.usecase.CreateReservationUseCase
import com.rajawali.core.domain.usecase.GetAirportsUseCase
import com.rajawali.core.domain.usecase.GetAvailableSeatsUseCase
import com.rajawali.core.domain.usecase.GetFlightByDate
import com.rajawali.core.domain.usecase.GetFlightsUseCase
import com.rajawali.core.domain.usecase.GetMealsUseCase
import com.rajawali.core.domain.usecase.GetPreferredFlight
import com.rajawali.core.domain.usecase.GetRecentSearchUseCase
import com.rajawali.core.domain.usecase.GetReservationByIdUseCase
import com.rajawali.core.domain.usecase.LoginUseCase
import com.rajawali.core.domain.usecase.PayReservationUseCase
import com.rajawali.core.domain.usecase.RegisterUseCase
import com.rajawali.core.domain.usecase.VerifyUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetAirportsUseCase(get()) }
    single { AddRecentSearchUseCase(get()) }
    single { GetRecentSearchUseCase(get()) }
    single { GetFlightsUseCase(get()) }
    single { GetPreferredFlight(get()) }
    single { GetFlightByDate() }
    single { CreatePassengerInformationInputUseCase() }
    single { GetMealsUseCase(get()) }
    single { CreateReservationUseCase(get()) }
    single { GetAvailableSeatsUseCase(get()) }
    single { PayReservationUseCase(get()) }
    single { GetReservationByIdUseCase(get()) }
    single { ClearRecentSearchUseCase(get()) }
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { VerifyUseCase(get()) }
}
