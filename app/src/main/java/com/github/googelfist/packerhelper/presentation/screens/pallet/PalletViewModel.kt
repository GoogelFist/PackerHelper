package com.github.googelfist.packerhelper.presentation.screens.pallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletEvent
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletState
import kotlinx.coroutines.launch

class PalletViewModel : ViewModel(), EventHandler<PalletEvent> {

    private val _result = MutableLiveData<PalletState>()
    val result = _result

    override fun obtainEvent(event: PalletEvent) {
        when (event) {
            is PalletEvent.CalculateRealGross -> calculated(event)
            is PalletEvent.CalculateInitParams -> calculatedInitParams(event)
        }
    }

    private fun calculated(event: PalletEvent.CalculateRealGross) {
        viewModelScope.launch {
            val clearWeight = event.boxWeight * event.boxCount

            val theoreticalGross = clearWeight + event.packageWeight + event.trayWeight
            val realGross = event.grossWeight
            val allow = clearWeight / 100

            val lowerBorderGross = theoreticalGross - allow
            val highestBorderGross = theoreticalGross + allow

            val isCorrect = event.grossWeight in (lowerBorderGross..highestBorderGross)
            val isLess = event.grossWeight < lowerBorderGross
            val isMore = event.grossWeight > highestBorderGross

            _result.value = when {
                isCorrect -> PalletState.Correct(clearWeight, theoreticalGross, allow, realGross, event.grossWeight - theoreticalGross)
                isLess -> PalletState.Less(clearWeight, theoreticalGross, allow, realGross, event.grossWeight - lowerBorderGross)
                isMore -> PalletState.More(clearWeight, theoreticalGross, allow, realGross, event.grossWeight - highestBorderGross)
                else -> PalletState.None
            }
        }
    }

    private fun calculatedInitParams(event: PalletEvent.CalculateInitParams) {
        val boxCount = when {
            event.boxWeight == 0f -> 0
            event.boxWeight <= 16f -> 60
            event.boxWeight > 16f && event.boxWeight < 19 -> 48
            event.boxWeight >= 19f -> 36
            else -> 0
        }
        val packageWeight = when (boxCount) {
            36 -> PACKAGE_WEIGHT_36
            48 -> PACKAGE_WEIGHT_48
            60 -> PACKAGE_WEIGHT_60
            else -> 0f
        }
        _result.value = PalletState.InitState(boxCount = boxCount, packageWeight = packageWeight)
    }

    companion object {
        private const val PACKAGE_WEIGHT_36 = 13f
        private const val PACKAGE_WEIGHT_48 = 17f
        private const val PACKAGE_WEIGHT_60 = 21f
    }
}