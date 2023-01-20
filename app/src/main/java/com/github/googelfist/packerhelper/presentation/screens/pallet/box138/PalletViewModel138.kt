package com.github.googelfist.packerhelper.presentation.screens.pallet.box138

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.googelfist.packerhelper.domain.usecases.LoadPallet138UseCase
import com.github.googelfist.packerhelper.domain.usecases.SavePackageWeight138UseCase
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pallet.box138.model.Limit138
import com.github.googelfist.packerhelper.presentation.screens.pallet.box138.model.PalletEvent138
import com.github.googelfist.packerhelper.presentation.screens.pallet.box138.model.PalletState138
import javax.inject.Inject

class Pallet138ViewModel(
    private val savePackageWeight138UseCase: SavePackageWeight138UseCase,
    private val loadPallet138UseCase: LoadPallet138UseCase
) : ViewModel(), EventHandler<PalletEvent138> {

    private val _result = MutableLiveData<PalletState138>()
    val result = _result

    override fun obtainEvent(event: PalletEvent138) {
        when (event) {
            is PalletEvent138.LoadPallet -> loadedPallet(event.boxWeight)
            is PalletEvent138.CalculateRealGross -> calculated(event)
            PalletEvent138.ClearFields -> cleared()
        }
    }

    private fun cleared() {
        _result.value = PalletState138.ClearState
    }

    private fun loadedPallet(boxWeight: Float) {
        val pallet = loadPallet138UseCase(boxWeight)
        _result.value = PalletState138.InitState(
            boxWeight = pallet.boxWeight,
            boxCount = pallet.boxCount,
            packageWeight = pallet.packageWeight
        )
    }

    private fun calculated(event: PalletEvent138.CalculateRealGross) {

        val clearWeight = event.boxWeight * event.boxCount

        val theoreticalGross = clearWeight + event.packageWeight + event.trayWeight
        val realGross = event.grossWeight
        val limitValue = clearWeight / 100

        val minGrossLimit = theoreticalGross - limitValue
        val maxGrossLimit = theoreticalGross + limitValue

        val isCorrect = event.grossWeight in (minGrossLimit..maxGrossLimit)
        val isLess = event.grossWeight < minGrossLimit
        val isMore = event.grossWeight > maxGrossLimit

        savePackageWeight138UseCase(boxCount = event.boxCount, packageWeight = event.packageWeight)

        _result.value = when {
            isCorrect -> PalletState138.Correct(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit138 = Limit138(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - theoreticalGross
            )
            isLess -> PalletState138.Less(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit138 = Limit138(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - minGrossLimit
            )
            isMore -> PalletState138.More(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit138 = Limit138(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - maxGrossLimit
            )
            else -> PalletState138.InitState()
        }
    }
}

class Pallet138ViewModelFactory @Inject constructor(
    private val savePackageWeight138UseCase: SavePackageWeight138UseCase,
    private val loadPallet138UseCase: LoadPallet138UseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Pallet138ViewModel::class.java)) {
            return Pallet138ViewModel(
                savePackageWeight138UseCase = savePackageWeight138UseCase,
                loadPallet138UseCase = loadPallet138UseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}