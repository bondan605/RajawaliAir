package com.rajawali.core.di

import com.rajawali.core.data.local.dataStorePreference.DataStore
import com.rajawali.core.data.local.dataStorePreference.userDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataStorePreferenceModule = module {
    single { DataStore(androidApplication().userDataStore) }

}