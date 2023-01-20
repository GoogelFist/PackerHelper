package com.github.googelfist.packerhelper.presentation.screens.pallet.box168.model

sealed class PalletState168 {
    data class Less(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit168: Limit168,
        val realGross: Float,
        val diff: Float
    ) : PalletState168()

    data class Correct(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit168: Limit168,
        val realGross: Float,
        val diff: Float
    ) : PalletState168()

    data class More(
        val clearWeight: Float,
        val theoreticalGross: Float,
        val limit168: Limit168,
        val realGross: Float,
        val diff: Float
    ) : PalletState168()

    data class InitState(
        val boxWeight: Float = 0f,
        val boxCount: Int = 0,
        val packageWeight: Float = 0f
    ) : PalletState168()

    object ClearState : PalletState168()
}

data class Limit168(val value: Float, val minGrossLimit: Float, val maxGrossLimit: Float)