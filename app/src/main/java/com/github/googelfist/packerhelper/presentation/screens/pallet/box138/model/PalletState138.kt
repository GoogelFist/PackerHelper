package com.github.googelfist.packerhelper.presentation.screens.pallet.box138.model

sealed class PalletState138 {
    data class Less(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit138: Limit138,
        val realGross: Float,
        val diff: Float
    ) : PalletState138()

    data class Correct(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit138: Limit138,
        val realGross: Float,
        val diff: Float
    ) : PalletState138()

    data class More(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit138: Limit138,
        val realGross: Float,
        val diff: Float
    ) : PalletState138()

    data class InitState(
        val boxWeight: Float = 0f,
        val boxCount: Int = 0,
        val packageWeight: Float = 0f
    ) : PalletState138()

    object ClearState : PalletState138()
}

data class Limit138(val value: Float, val minGrossLimit: Float, val maxGrossLimit: Float)