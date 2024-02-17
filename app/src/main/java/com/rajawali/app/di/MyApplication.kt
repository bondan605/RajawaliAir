package com.rajawali.app.di

import android.app.Application
import com.rajawali.core.di.dataStorePreferenceModule
import com.rajawali.core.di.databaseModule
import com.rajawali.core.di.localRepository
import com.rajawali.core.di.remoteModule
import com.rajawali.core.di.remoteRepository
import com.rajawali.core.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    dataStorePreferenceModule,
                    remoteModule,
                    remoteRepository,
                    localRepository,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }

        //timber
//        if (BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
//            Timber.plant(ReleaseTree())

    }
}

