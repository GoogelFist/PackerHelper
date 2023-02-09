package com.github.googelfist.packerhelper.presentation.screens.pallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.MainPalletFragmentBinding
import com.github.googelfist.packerhelper.presentation.screens.pallet.box138.PalletFragment138
import com.github.googelfist.packerhelper.presentation.screens.pallet.box168.PalletFragment168
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.PalletFragment208
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainPalletFragment : Fragment(R.layout.main_pallet_fragment) {

    private var _binding: MainPalletFragmentBinding? = null
    private val binding: MainPalletFragmentBinding
        get() = _binding!!

    private val palletFragments = listOf(
        PalletFragment208.newInstance(),
        PalletFragment168.newInstance(),
        PalletFragment138.newInstance()
    )

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainPalletFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = ViewPagerAdapter(this, palletFragments)
        viewPager = binding.palletViewpager
        tabLayout = binding.palletTabLayout

        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getTabLabel(palletFragments, position)
                1 -> tab.text = getTabLabel(palletFragments, position)
                2 -> tab.text = getTabLabel(palletFragments, position)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTabLabel(palletFragments: List<Fragment>, position: Int): String {
        return palletFragments[position].javaClass.name.takeLast(3)
    }
}