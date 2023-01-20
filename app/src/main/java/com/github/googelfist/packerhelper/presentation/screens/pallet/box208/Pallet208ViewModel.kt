package com.github.googelfist.packerhelper.presentation.screens.pallet.box208

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.googelfist.packerhelper.domain.usecases.LoadPallet208UseCase
import com.github.googelfist.packerhelper.domain.usecases.SavePackageWeight208UseCase
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model.Limit208
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model.PalletEvent208
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model.PalletState208
import javax.inject.Inject

class Pallet208ViewModel(
    private val savePackageWeight208UseCase: SavePackageWeight208UseCase,
    private val loadPallet208UseCase: LoadPallet208UseCase
) : ViewModel(), EventHandler<PalletEvent208> {

    private val _result = MutableLiveData<PalletState208>()
    val result = _result

    override fun obtainEvent(event: PalletEvent208) {
        when (event) {
            is PalletEvent208.LoadPallet -> loadedPallet(event.boxWeight)
            is PalletEvent208.CalculateRealGross -> calculated(event)
            PalletEvent208.ClearFields -> cleared()
        }
    }

    private fun cleared() {
        _result.value = PalletState208.ClearState
    }

    private fun loadedPallet(boxWeight: Float) {
        val pallet = loadPallet208UseCase(boxWeight)
        _result.value = PalletState208.InitState(
            boxWeight = pallet.boxWeight,
            boxCount = pallet.boxCount,
            packageWeight = pallet.packageWeight
        )
    }

    private fun calculated(event: PalletEvent208.CalculateRealGross) {

        val clearWeight = event.boxWeight * event.boxCount

        val theoreticalGross = clearWeight + event.packageWeight + event.trayWeight
        val realGross = event.grossWeight
        val limitValue = clearWeight / 100

        val minGrossLimit = theoreticalGross - limitValue
        val maxGrossLimit = theoreticalGross + limitValue

        val isCorrect = event.grossWeight in (minGrossLimit..maxGrossLimit)
        val isLess = event.grossWeight < minGrossLimit
        val isMore = event.grossWeight > maxGrossLimit

        savePackageWeight208UseCase(boxCount = event.boxCount, packageWeight = event.packageWeight)

        _result.value = when {
            isCorrect -> PalletState208.Correct(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit208 = Limit208(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - theoreticalGross
            )
            isLess -> PalletState208.Less(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit208 = Limit208(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - minGrossLimit
            )
            isMore -> PalletState208.More(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit208 = Limit208(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - maxGrossLimit
            )
            else -> PalletState208.InitState()
        }
    }
}

class Pallet208ViewModelFactory @Inject constructor(
    private val savePackageWeight208UseCase: SavePackageWeight208UseCase,
    private val loadPallet208UseCase: LoadPallet208UseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Pallet208ViewModel::class.java)) {
            return Pallet208ViewModel(
                savePackageWeight208UseCase = savePackageWeight208UseCase,
                loadPallet208UseCase = loadPallet208UseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}