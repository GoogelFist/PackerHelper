package com.github.googelfist.packerhelper.data

import android.content.SharedPreferences
import com.github.googelfist.packerhelper.domain.PalletRepository
import com.github.googelfist.packerhelper.domain.model.Pallet
import javax.inject.Inject

class Pallet138RepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : PalletRepository {

    override fun savePackageWeight(boxCount: Int, packageWeight: Float) {
        sharedPreferences.edit()
            .putFloat("package138Weight_$boxCount", packageWeight)
            .apply()
    }

    override fun loadPallet(boxWeight: Float): Pallet {
        val boxCount = when {
            boxWeight == 0f -> 0
            boxWeight <= 14f -> 60
            boxWeight > 14f -> 48
            else -> 0
        }

        val defValue = when (boxCount) {
            48 -> PACKAGE_WEIGHT_48
            60 -> PACKAGE_WEIGHT_60
            else -> 0f
        }

        val packageWeight = sharedPreferences.getFloat("package138Weight_$boxCount", defValue)

        return Pallet(boxWeight = boxWeight, boxCount = boxCount, packageWeight = packageWeight)
    }

    companion object {
        private const val PACKAGE_WEIGHT_48 = 14.6f
        private const val PACKAGE_WEIGHT_60 = 18f
    }
}