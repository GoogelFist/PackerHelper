package com.github.googelfist.packerhelper.presentation.screens.pallet.box138.model

sealed class PalletEvent138 {
    data class CalculateRealGross(
        val boxWeight: Float = 0f,
        val trayWeight: Float = 0f,
        val grossWeight: Float = 0f,
        val boxCount: Int = 0,
        val packageWeight: Float = 0f
    ) : PalletEvent138()

    data class LoadPallet(val boxWeight: Float = 0f) : PalletEvent138()

    object ClearFields: PalletEvent138()
}