package com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model

sealed class PalletEvent208 {
    data class CalculateRealGross(
        val boxWeight: Float = 0f,
        val trayWeight: Float = 0f,
        val grossWeight: Float = 0f,
        val boxCount: Int = 0,
        val packageWeight: Float = 0f
    ) : PalletEvent208()

    data class LoadPallet(val boxWeight: Float = 0f) : PalletEvent208()

    object ClearFields: PalletEvent208()
}