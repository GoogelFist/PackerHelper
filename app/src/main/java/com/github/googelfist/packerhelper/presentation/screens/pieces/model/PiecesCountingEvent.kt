package com.github.googelfist.packerhelper.presentation.screens.pieces.model

sealed class PiecesCountingEvent {
    data class Calculate(val boxWeight: Float = 0f, val weight100pcs: Float = 0f) :
        PiecesCountingEvent()
    object ClearFields: PiecesCountingEvent()
}