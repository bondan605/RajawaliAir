package com.rajawali.core.di

import com.rajawali.core.domain.usecase.AddRecentSearchUseCase
import com.rajawali.core.domain.usecase.ClearRecentSearchUseCase
import com.rajawali.core.domain.usecase.CreateLoginSessionUseCase
import com.rajawali.core.domain.usecase.CreatePassengerInformationInputUseCase
import com.rajawali.core.domain.usecase.CreateReservationUseCase
import com.rajawali.core.domain.usecase.FilterSeatsUseCase
import com.rajawali.core.domain.usecase.GetAirportsUseCase
import com.rajawali.core.domain.usecase.GetAvailableSeatsUseCase
import com.rajawali.core.domain.usecase.GetFlightByDate
import com.rajawali.core.domain.usecase.GetFlightsUseCase
import com.rajawali.core.domain.usecase.GetLoggedInDataUseCase
import com.rajawali.core.domain.usecase.GetMealsUseCase
import com.rajawali.core.domain.usecase.GetNotificationUseCase
import com.rajawali.core.domain.usecase.GetPreferredFlight
import com.rajawali.core.domain.usecase.GetRecentSearchUseCase
import com.rajawali.core.domain.usecase.GetReservationByIdUseCase
import com.rajawali.core.domain.usecase.IsLoginUseCase
import com.rajawali.core.domain.usecase.LoginUseCase
import com.rajawali.core.domain.usecase.LogoutUseCase
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
    single { FilterSeatsUseCase() }

    //dataPreference
    single { CreateLoginSessionUseCase(get()) }
    single { IsLoginUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { GetLoggedInDataUseCase(get()) }

    single { GetNotificationUseCase(get()) }

}
