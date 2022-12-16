package com.github.googelfist.packerhelper.presentation.screens.pallet.model

sealed class PalletEvent {
    data class CalculateRealGross(
        val boxWeight: Float = 0f,
        val trayWeight: Float = 0f,
        val grossWeight: Float = 0f,
        val boxCount: Int = 0,
        val packageWeight: Float = 0f
    ) : PalletEvent()

    data class CalculateInitParams(val boxWeight: Float = 0f) : PalletEvent()
}