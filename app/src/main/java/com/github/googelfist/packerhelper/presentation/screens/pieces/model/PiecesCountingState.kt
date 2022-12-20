package com.github.googelfist.packerhelper.presentation.screens.pieces.model

sealed class PiecesCountingState {
    data class Counted(val boxWeight: Float = 0f, val pcs: Int = 0): PiecesCountingState()
    object Init : PiecesCountingState()
}