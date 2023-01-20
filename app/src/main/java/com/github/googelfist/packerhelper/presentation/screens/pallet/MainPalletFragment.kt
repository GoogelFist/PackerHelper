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

    private val list = listOf(
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

        viewPagerAdapter = ViewPagerAdapter(this, list)
        viewPager = binding.palletViewpager
        tabLayout = binding.palletTabLayout

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = TAB_LABEL_208
                1 -> tab.text = TAB_LABEL_168
                2 -> tab.text = TAB_LABEL_138
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAB_LABEL_208 = "208"
        private const val TAB_LABEL_168 = "168"
        private const val TAB_LABEL_138 = "138"
    }
}