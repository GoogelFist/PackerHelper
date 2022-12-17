package com.github.googelfist.packerhelper.domain

import javax.inject.Inject

class SavePackageWeightUseCase @Inject constructor(private val palletRepository: PalletRepository) {
    operator fun invoke(boxCount: Int, packageWeight: Float) {
        palletRepository.savePackageWeight(boxCount, packageWeight)
    }
}