package com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model

sealed class PalletState208 {
    data class Less(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit208: Limit208,
        val realGross: Float,
        val diff: Float
    ) : PalletState208()

    data class Correct(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit208: Limit208,
        val realGross: Float,
        val diff: Float
    ) : PalletState208()

    data class More(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit208: Limit208,
        val realGross: Float,
        val diff: Float
    ) : PalletState208()

    data class InitState(
        val boxWeight: Float = 0f,
        val boxCount: Int = 0,
        val packageWeight: Float = 0f
    ) : PalletState208()

    object ClearState : PalletState208()
}

data class Limit208(val value: Float, val minGrossLimit: Float, val maxGrossLimit: Float)