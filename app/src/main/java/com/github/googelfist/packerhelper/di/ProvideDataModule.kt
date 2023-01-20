package com.github.googelfist.packerhelper.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.googelfist.packerhelper.data.Pallet138RepositoryImpl
import com.github.googelfist.packerhelper.data.Pallet168RepositoryImpl
import com.github.googelfist.packerhelper.data.Pallet208RepositoryImpl
import com.github.googelfist.packerhelper.domain.PalletRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ProvideDataModule {

    @Provides
    @Singleton
    fun provideSharedPrefs208(application: Application): SharedPreferences {
        return application.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Named(PALLET_REPOSITORY_208)
    @Singleton
    fun providePallet208Repository(sharedPreferences: SharedPreferences): PalletRepository {
        return Pallet208RepositoryImpl(sharedPreferences)
    }

    @Provides
    @Named(PALLET_REPOSITORY_168)
    @Singleton
    fun providePallet168Repository(sharedPreferences: SharedPreferences): PalletRepository {
        return Pallet168RepositoryImpl(sharedPreferences)
    }

    @Provides
    @Named(PALLET_REPOSITORY_138)
    @Singleton
    fun providePallet138Repository(sharedPreferences: SharedPreferences): PalletRepository {
        return Pallet138RepositoryImpl(sharedPreferences)
    }

    companion object {
        private const val PREFS_FILE_NAME = "shared_prefs"

        const val PALLET_REPOSITORY_208 = "pallet_repository_208"
        const val PALLET_REPOSITORY_168 = "pallet_repository_168"
        const val PALLET_REPOSITORY_138 = "pallet_repository_138"
    }
}