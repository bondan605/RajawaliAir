package com.rajawali.app.di

import com.rajawali.app.presentation.addsOn.CreateReservationViewModel
import com.rajawali.app.presentation.addsOn.seat.SeatsViewModel
import com.rajawali.app.presentation.bottomSheetDialog.flightDetail.FlightDetailBottomSheetViewModel
import com.rajawali.app.presentation.chooseTicket.ChooseTicketViewModel
import com.rajawali.app.presentation.detailsInformation.DetailsInformationViewModel
import com.rajawali.app.presentation.notification.NotificationViewModel
import com.rajawali.app.presentation.payment.PaymentViewModel
import com.rajawali.app.presentation.paymentPending.PaymentPendingViewModel
import com.rajawali.app.presentation.paymentWaiting.PaymentWaitingViewModel
import com.rajawali.app.presentation.pickCity.AirportsViewModel
import com.rajawali.app.presentation.pickCity.SearchViewModel
import com.rajawali.app.presentation.splash.SplashViewModel
import com.rajawali.app.presentation.ui.auth.login.LoginViewModel
import com.rajawali.app.presentation.ui.auth.register.RegisterViewModel
import com.rajawali.app.presentation.ui.auth.verification.VerifyViewModel
import com.rajawali.core.presentation.viewModel.MealsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get(), get(), get(), get()) }
    viewModel { AirportsViewModel() }
    viewModel { ChooseTicketViewModel(get(), get()) }
    viewModel { DetailsInformationViewModel(get()) }
    viewModel { FlightDetailBottomSheetViewModel() }
//    viewModel { TravelAddsOnViewModel() }
    viewModel { MealsViewModel(get()) }
    viewModel { SeatsViewModel(get(), get()) }
    viewModel { CreateReservationViewModel(get()) }
    viewModel { PaymentViewModel(get()) }
    viewModel { PaymentWaitingViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { VerifyViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { NotificationViewModel(get(), get()) }
    viewModel { PaymentPendingViewModel(get()) }
}