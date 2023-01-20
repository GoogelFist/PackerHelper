package com.github.googelfist.packerhelper.domain.usecases

import com.github.googelfist.packerhelper.di.ProvideDataModule.Companion.PALLET_REPOSITORY_138
import com.github.googelfist.packerhelper.domain.PalletRepository
import com.github.googelfist.packerhelper.domain.model.Pallet
import javax.inject.Inject
import javax.inject.Named

class LoadPallet138UseCase @Inject constructor(@Named(PALLET_REPOSITORY_138) private val palletRepository: PalletRepository) {
    operator fun invoke(boxWeight: Float): Pallet {
        return palletRepository.loadPallet(boxWeight)
    }
}