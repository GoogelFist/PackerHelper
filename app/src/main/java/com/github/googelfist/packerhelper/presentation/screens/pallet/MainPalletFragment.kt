package com.github.googelfist.packerhelper.presentation.screens.pallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.MainPalletFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainPalletFragment : Fragment(R.layout.main_pallet_fragment) {

    private var _binding: MainPalletFragmentBinding? = null
    private val binding: MainPalletFragmentBinding
        get() = _binding!!

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

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = binding.palletViewpager
        tabLayout = binding.palletTabLayout

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = "${position + 1}"
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}