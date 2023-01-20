package com.github.googelfist.packerhelper.presentation.screens.pallet

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.PalletFragment208

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return PalletFragment208.newInstance()
    }
}