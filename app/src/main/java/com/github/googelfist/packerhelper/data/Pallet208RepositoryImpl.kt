package com.github.googelfist.packerhelper.data

import android.content.SharedPreferences
import com.github.googelfist.packerhelper.di.ProvideDataModule.Companion.SHARED_PREFS_208
import com.github.googelfist.packerhelper.domain.PalletRepository
import com.github.googelfist.packerhelper.domain.model.Pallet
import javax.inject.Inject
import javax.inject.Named

class Pallet208RepositoryImpl @Inject constructor(@Named(SHARED_PREFS_208) private val sharedPreferences: SharedPreferences) : PalletRepository {

    override fun savePackageWeight(boxCount: Int, packageWeight: Float) {
        sharedPreferences.edit()
            .putFloat("packageWeight208$boxCount", packageWeight)
            .apply()
    }

    override fun loadPallet(boxWeight: Float): Pallet {
        val boxCount = when {
            boxWeight == 0f -> 0
            boxWeight <= 16f -> 60
            boxWeight > 16f && boxWeight < 19 -> 48
            boxWeight >= 19f -> 36
            else -> 0
        }

        val defValue = when (boxCount) {
            36 -> PACKAGE_WEIGHT_36
            48 -> PACKAGE_WEIGHT_48
            60 -> PACKAGE_WEIGHT_60
            else -> 0f
        }

        val packageWeight = sharedPreferences.getFloat("packageWeight208$boxCount", defValue)

        return Pallet(boxWeight = boxWeight, boxCount = boxCount, packageWeight = packageWeight)
    }

    companion object {
        private const val PACKAGE_WEIGHT_36 = 13f
        private const val PACKAGE_WEIGHT_48 = 17f
        private const val PACKAGE_WEIGHT_60 = 21f
    }
}