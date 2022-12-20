package com.github.googelfist.packerhelper.presentation.screens.pallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.googelfist.packerhelper.domain.LoadPalletUseCase
import com.github.googelfist.packerhelper.domain.SavePackageWeightUseCase
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.Limit
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletEvent
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletState
import javax.inject.Inject

class PalletViewModel(
    private val savePackageWeightUseCase: SavePackageWeightUseCase,
    private val loadPalletUseCase: LoadPalletUseCase
) : ViewModel(), EventHandler<PalletEvent> {

    private val _result = MutableLiveData<PalletState>()
    val result = _result

    override fun obtainEvent(event: PalletEvent) {
        when (event) {
            is PalletEvent.LoadPallet -> loadedPallet(event.boxWeight)
            is PalletEvent.CalculateRealGross -> calculated(event)
        }
    }

    private fun loadedPallet(boxWeight: Float) {
        val pallet = loadPalletUseCase(boxWeight)
        _result.value = PalletState.InitState(
            boxWeight = pallet.boxWeight,
            boxCount = pallet.boxCount,
            packageWeight = pallet.packageWeight
        )
    }

    private fun calculated(event: PalletEvent.CalculateRealGross) {

        val clearWeight = event.boxWeight * event.boxCount

        val theoreticalGross = clearWeight + event.packageWeight + event.trayWeight
        val realGross = event.grossWeight
        val limitValue = clearWeight / 100

        val minGrossLimit = theoreticalGross - limitValue
        val maxGrossLimit = theoreticalGross + limitValue

        val isCorrect = event.grossWeight in (minGrossLimit..maxGrossLimit)
        val isLess = event.grossWeight < minGrossLimit
        val isMore = event.grossWeight > maxGrossLimit

        savePackageWeightUseCase(boxCount = event.boxCount, packageWeight = event.packageWeight)

        _result.value = when {
            isCorrect -> PalletState.Correct(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit = Limit(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - theoreticalGross
            )
            isLess -> PalletState.Less(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit = Limit(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - minGrossLimit
            )
            isMore -> PalletState.More(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit = Limit(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - maxGrossLimit
            )
            else -> PalletState.InitState()
        }
    }
}

class PalletViewModelFactory @Inject constructor(
    private val savePackageWeightUseCase: SavePackageWeightUseCase,
    private val loadPalletUseCase: LoadPalletUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PalletViewModel::class.java)) {
            return PalletViewModel(
                savePackageWeightUseCase = savePackageWeightUseCase,
                loadPalletUseCase = loadPalletUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}