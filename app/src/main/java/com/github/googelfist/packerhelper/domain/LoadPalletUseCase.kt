package com.github.googelfist.packerhelper.domain

import com.github.googelfist.packerhelper.domain.model.Pallet
import javax.inject.Inject

class LoadPalletUseCase @Inject constructor(private val palletRepository: PalletRepository) {
    operator fun invoke(boxWeight: Float): Pallet {
        return palletRepository.loadPallet(boxWeight)
    }
}