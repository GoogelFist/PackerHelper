package com.github.googelfist.packerhelper.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProvideDataModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(application: Application): SharedPreferences {
        return application.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_FILE_NAME = "shared_prefs"
    }
}