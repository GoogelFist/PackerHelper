package com.github.googelfist.packerhelper.domain

import com.github.googelfist.packerhelper.domain.model.Pallet

interface PalletRepository {
    fun savePackageWeight(boxCount: Int, packageWeight: Float)

    fun loadPallet(boxWeight: Float): Pallet
}