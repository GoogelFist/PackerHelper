package com.github.googelfist.packerhelper.presentation.screens.pallet.model

sealed class PalletState {
    data class Less(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val allow: Float,
        val realGross: Float,
        val diff: Float
    ) : PalletState()

    data class Correct(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val allow: Float,
        val realGross: Float,
        val diff: Float
    ) : PalletState()

    data class More(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val allow: Float,
        val realGross: Float,
        val diff: Float
    ) : PalletState()

    data class InitState(val boxCount: Int = 0, val packageWeight: Float = 0f) : PalletState()
    object None : PalletState()
}