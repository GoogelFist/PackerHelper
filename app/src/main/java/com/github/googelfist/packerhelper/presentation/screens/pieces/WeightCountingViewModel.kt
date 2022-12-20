package com.github.googelfist.packerhelper.presentation.screens.pieces

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pieces.model.WeightCountingEvent
import com.github.googelfist.packerhelper.presentation.screens.pieces.model.WeightCountingState

class WeightCountingViewModel : ViewModel(), EventHandler<WeightCountingEvent> {

    private val _result = MutableLiveData<WeightCountingState>()
    val result = _result

    init {
        _result.value = WeightCountingState.Init
    }

    override fun obtainEvent(event: WeightCountingEvent) {
        when (event) {
            is WeightCountingEvent.Calculate -> calculatedWeight(event)
            WeightCountingEvent.ClearFields -> cleared()
        }
    }

    private fun cleared() {
        _result.value = WeightCountingState.Init
    }

    private fun calculatedWeight(event: WeightCountingEvent.Calculate) {
        val boxWeight = if (event.weight100pcs == 0f) {
            0
        } else {
            (event.weight100pcs * event.boxPieces) / 100
        }

        _result.value = WeightCountingState.Counted(boxWeight.toFloat(), event.boxPieces)
    }
}