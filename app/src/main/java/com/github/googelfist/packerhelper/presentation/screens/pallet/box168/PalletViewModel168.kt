package com.github.googelfist.packerhelper.presentation.screens.pallet.box168

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.googelfist.packerhelper.domain.usecases.LoadPallet168UseCase
import com.github.googelfist.packerhelper.domain.usecases.SavePackageWeight168UseCase
import com.github.googelfist.packerhelper.presentation.screens.EventHandler
import com.github.googelfist.packerhelper.presentation.screens.pallet.box168.model.Limit168
import com.github.googelfist.packerhelper.presentation.screens.pallet.box168.model.PalletEvent168
import com.github.googelfist.packerhelper.presentation.screens.pallet.box168.model.PalletState168
import javax.inject.Inject

class Pallet168ViewModel(
    private val savePackageWeight168UseCase: SavePackageWeight168UseCase,
    private val loadPallet168UseCase: LoadPallet168UseCase
) : ViewModel(), EventHandler<PalletEvent168> {

    private val _result = MutableLiveData<PalletState168>()
    val result = _result

    override fun obtainEvent(event: PalletEvent168) {
        when (event) {
            is PalletEvent168.LoadPallet -> loadedPallet(event.boxWeight)
            is PalletEvent168.CalculateRealGross -> calculated(event)
            PalletEvent168.ClearFields -> cleared()
        }
    }

    private fun cleared() {
        _result.value = PalletState168.ClearState
    }

    private fun loadedPallet(boxWeight: Float) {
        val pallet = loadPallet168UseCase(boxWeight)
        _result.value = PalletState168.InitState(
            boxWeight = pallet.boxWeight,
            boxCount = pallet.boxCount,
            packageWeight = pallet.packageWeight
        )
    }

    private fun calculated(event: PalletEvent168.CalculateRealGross) {

        val clearWeight = event.boxWeight * event.boxCount

        val theoreticalGross = clearWeight + event.packageWeight + event.trayWeight
        val realGross = event.grossWeight
        val limitValue = clearWeight / 100

        val minGrossLimit = theoreticalGross - limitValue
        val maxGrossLimit = theoreticalGross + limitValue

        val isCorrect = event.grossWeight in (minGrossLimit..maxGrossLimit)
        val isLess = event.grossWeight < minGrossLimit
        val isMore = event.grossWeight > maxGrossLimit

        savePackageWeight168UseCase(boxCount = event.boxCount, packageWeight = event.packageWeight)

        _result.value = when {
            isCorrect -> PalletState168.Correct(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit168 = Limit168(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - theoreticalGross
            )
            isLess -> PalletState168.Less(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit168 = Limit168(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - minGrossLimit
            )
            isMore -> PalletState168.More(
                clearWeight = clearWeight,
                theoreticalGross = theoreticalGross,
                limit168 = Limit168(limitValue, minGrossLimit, maxGrossLimit),
                realGross = realGross,
                diff = event.grossWeight - maxGrossLimit
            )
            else -> PalletState168.InitState()
        }
    }
}

class Pallet168ViewModelFactory @Inject constructor(
    private val savePackageWeight168UseCase: SavePackageWeight168UseCase,
    private val loadPallet168UseCase: LoadPallet168UseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Pallet168ViewModel::class.java)) {
            return Pallet168ViewModel(
                savePackageWeight168UseCase = savePackageWeight168UseCase,
                loadPallet168UseCase = loadPallet168UseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}