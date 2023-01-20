package com.github.googelfist.packerhelper.di

import android.app.Application
import com.github.googelfist.packerhelper.presentation.screens.pallet.box138.PalletFragment138
import com.github.googelfist.packerhelper.presentation.screens.pallet.box168.PalletFragment168
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.PalletFragment208
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BindDataModule::class, ProvideDataModule::class])
interface ApplicationComponent {

    fun inject(palletFragment: PalletFragment208)

    fun inject(palletFragment: PalletFragment168)

    fun inject(palletFragment: PalletFragment138)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: Application): Builder

        fun build(): ApplicationComponent
    }
}