package com.github.googelfist.packerhelper.di

import com.github.googelfist.packerhelper.data.Pallet208RepositoryImpl
import com.github.googelfist.packerhelper.domain.PalletRepository
import dagger.Binds
import dagger.Module

@Module
interface BindDataModule {

    @Binds
    fun bindPalletRepository(impl: Pallet208RepositoryImpl): PalletRepository
}