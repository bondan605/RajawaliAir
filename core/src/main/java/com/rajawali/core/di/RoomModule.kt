package com.rajawali.core.di

import androidx.room.Room
import com.rajawali.core.data.local.room.database.RajawaliDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


/*
    delete fallbackToDestructiveMigration()
    when app is released
*/
val databaseModule = module {
    single {
        factory { get<RajawaliDatabase>().recentSearchDao() }
        Room.databaseBuilder(
            androidContext(),
            RajawaliDatabase::class.java,
            "RajawaliDatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }
}