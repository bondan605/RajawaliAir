package com.rajawali.app.di

import com.rajawali.app.presentation.pickCity.AirportsViewModel
import com.rajawali.app.presentation.pickCity.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { AirportsViewModel()}
}