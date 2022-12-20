package com.github.googelfist.packerhelper.presentation.screens.counting.model

sealed class WeightCountingEvent {
    data class Calculate(val boxPieces: Int = 0, val weight100pcs: Float = 0f) :
        WeightCountingEvent()
    object ClearFields: WeightCountingEvent()
}