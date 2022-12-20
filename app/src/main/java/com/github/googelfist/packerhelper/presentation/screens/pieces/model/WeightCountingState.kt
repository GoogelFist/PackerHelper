package com.github.googelfist.packerhelper.presentation.screens.pieces.model

sealed class WeightCountingState {
    data class Counted(val boxWeight: Float = 0f, val pcs: Int = 0): WeightCountingState()
    object Init : WeightCountingState()
}