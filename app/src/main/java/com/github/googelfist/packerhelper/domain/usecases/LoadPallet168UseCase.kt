package com.github.googelfist.packerhelper.domain.usecases

import com.github.googelfist.packerhelper.di.ProvideDataModule.Companion.PALLET_REPOSITORY_168
import com.github.googelfist.packerhelper.domain.PalletRepository
import com.github.googelfist.packerhelper.domain.model.Pallet
import javax.inject.Inject
import javax.inject.Named

class LoadPallet168UseCase @Inject constructor(@Named(PALLET_REPOSITORY_168) private val palletRepository: PalletRepository) {
    operator fun invoke(boxWeight: Float): Pallet {
        return palletRepository.loadPallet(boxWeight)
    }
}