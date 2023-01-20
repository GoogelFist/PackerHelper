package com.github.googelfist.packerhelper.domain.usecases

import com.github.googelfist.packerhelper.di.ProvideDataModule.Companion.PALLET_REPOSITORY_208
import com.github.googelfist.packerhelper.domain.PalletRepository
import javax.inject.Inject
import javax.inject.Named

class SavePackageWeight208UseCase @Inject constructor(@Named(PALLET_REPOSITORY_208) private val palletRepository: PalletRepository) {
    operator fun invoke(boxCount: Int, packageWeight: Float) {
        palletRepository.savePackageWeight(boxCount, packageWeight)
    }
}