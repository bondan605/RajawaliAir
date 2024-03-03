package com.rajawali.core.di

import com.rajawali.core.domain.usecase.airports.AddRecentSearchUseCase
import com.rajawali.core.domain.usecase.airports.ClearRecentSearchUseCase
import com.rajawali.core.domain.usecase.authentication.CreateLoginSessionUseCase
import com.rajawali.core.domain.usecase.tickets.CreatePassengerInformationInputUseCase
import com.rajawali.core.domain.usecase.reservation.CreateReservationUseCase
import com.rajawali.core.domain.usecase.addOns.FilterSeatsUseCase
import com.rajawali.core.domain.usecase.payment.FinishPaymentUseCase
import com.rajawali.core.domain.usecase.airports.GetAirportsUseCase
import com.rajawali.core.domain.usecase.addOns.GetAvailableSeatsUseCase
import com.rajawali.core.domain.usecase.tickets.GetFlightByDate
import com.rajawali.core.domain.usecase.tickets.GetFlightsUseCase
import com.rajawali.core.domain.usecase.authentication.GetLoggedInDataUseCase
import com.rajawali.core.domain.usecase.addOns.GetMealsUseCase
import com.rajawali.core.domain.usecase.GetNotificationUseCase
import com.rajawali.core.domain.usecase.tickets.GetPreferredFlight
import com.rajawali.core.domain.usecase.airports.GetRecentSearchUseCase
import com.rajawali.core.domain.usecase.reservation.GetReservationByIdUseCase
import com.rajawali.core.domain.usecase.authentication.IsLoginUseCase
import com.rajawali.core.domain.usecase.authentication.LoginUseCase
import com.rajawali.core.domain.usecase.authentication.LogoutUseCase
import com.rajawali.core.domain.usecase.payment.PayReservationUseCase
import com.rajawali.core.domain.usecase.authentication.RegisterUseCase
import com.rajawali.core.domain.usecase.authentication.VerifyUseCase
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
    single { FinishPaymentUseCase(get()) }

}
