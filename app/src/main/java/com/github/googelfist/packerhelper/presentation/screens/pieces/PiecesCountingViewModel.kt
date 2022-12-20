package com.github.googelfist.packerhelper.presentation.screens.pieces

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pieces.model.PiecesCountingEvent
import com.github.googelfist.packerhelper.presentation.screens.pieces.model.PiecesCountingState

class PiecesCountingViewModel : ViewModel(), EventHandler<PiecesCountingEvent> {

    private val _result = MutableLiveData<PiecesCountingState>()
    val result = _result

    init {
        _result.value = PiecesCountingState.Init
    }

    override fun obtainEvent(event: PiecesCountingEvent) {
        when (event) {
            is PiecesCountingEvent.Calculate -> calculatedPcs(event)
            PiecesCountingEvent.ClearFields -> cleared()
        }
    }

    private fun cleared() {
        _result.value = PiecesCountingState.Init
    }

    private fun calculatedPcs(event: PiecesCountingEvent.Calculate) {
        val pcs = if (event.weight100pcs == 0f) {
            0
        } else {
            (event.boxWeight * 100) / event.weight100pcs
        }
        _result.value = PiecesCountingState.Counted(event.boxWeight, pcs.toInt())
    }
}