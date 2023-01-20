package com.github.googelfist.packerhelper.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.googelfist.packerhelper.data.Pallet208RepositoryImpl
import com.github.googelfist.packerhelper.domain.PalletRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ProvideDataModule {

    @Provides
    @Named(SHARED_PREFS_208)
    @Singleton
    fun provideSharedPrefs208(application: Application): SharedPreferences {
        return application.getSharedPreferences(PREFS_208_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Named(PALLET_REPOSITORY_208)
    @Singleton
    fun providePallet208Repository(@Named(SHARED_PREFS_208) sharedPreferences: SharedPreferences): PalletRepository {
        return Pallet208RepositoryImpl(sharedPreferences)
    }

    @Provides
    @Named(SHARED_PREFS_168)
    @Singleton
    fun provideSharedPrefs168(application: Application): SharedPreferences {
        return application.getSharedPreferences(PREFS_168_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Named(PALLET_REPOSITORY_168)
    @Singleton
    fun providePallet168Repository(@Named(SHARED_PREFS_168) sharedPreferences: SharedPreferences): PalletRepository {
        return Pallet208RepositoryImpl(sharedPreferences)
    }

    @Provides
    @Named(SHARED_PREFS_138)
    @Singleton
    fun provideSharedPrefs138(application: Application): SharedPreferences {
        return application.getSharedPreferences(PREFS_138_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Named(PALLET_REPOSITORY_138)
    @Singleton
    fun providePallet138Repository(@Named(SHARED_PREFS_138) sharedPreferences: SharedPreferences): PalletRepository {
        return Pallet208RepositoryImpl(sharedPreferences)
    }

    companion object {
        private const val PREFS_208_FILE_NAME = "shared_prefs_208"
        private const val PREFS_168_FILE_NAME = "shared_prefs_168"
        private const val PREFS_138_FILE_NAME = "shared_prefs_138"

        const val SHARED_PREFS_208 = "shared_prefs_208"
        const val SHARED_PREFS_168 = "shared_prefs_168"
        const val SHARED_PREFS_138 = "shared_prefs_138"

        const val PALLET_REPOSITORY_208 = "pallet_repository_208"
        const val PALLET_REPOSITORY_168 = "pallet_repository_168"
        const val PALLET_REPOSITORY_138 = "pallet_repository_138"
    }
}