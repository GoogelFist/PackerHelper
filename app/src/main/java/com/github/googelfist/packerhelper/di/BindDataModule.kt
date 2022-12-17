package com.github.googelfist.packerhelper.di

import com.github.googelfist.packerhelper.data.PalletRepositoryImpl
import com.github.googelfist.packerhelper.domain.PalletRepository
import dagger.Binds
import dagger.Module

@Module
interface BindDataModule {

    @Binds
    fun bindPalletRepository(impl: PalletRepositoryImpl): PalletRepository
}