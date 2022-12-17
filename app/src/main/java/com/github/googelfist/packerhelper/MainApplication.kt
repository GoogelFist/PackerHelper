package com.github.googelfist.packerhelper

import android.app.Application
import android.content.Context
import com.github.googelfist.packerhelper.di.ApplicationComponent
import com.github.googelfist.packerhelper.di.DaggerApplicationComponent

class MainApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        appComponent = DaggerApplicationComponent.builder().context(this).build()
        super.onCreate()
    }
}

val Context.component: ApplicationComponent
    get() = when (this) {
        is MainApplication -> appComponent
        else -> this.applicationContext.component
    }