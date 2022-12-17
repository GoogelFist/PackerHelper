package com.github.googelfist.packerhelper.di

import android.app.Application
import com.github.googelfist.packerhelper.presentation.screens.pallet.PalletFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BindDataModule::class, ProvideDataModule::class])
interface ApplicationComponent {

    fun inject(palletFragment: PalletFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: Application): Builder

        fun build(): ApplicationComponent
    }
}