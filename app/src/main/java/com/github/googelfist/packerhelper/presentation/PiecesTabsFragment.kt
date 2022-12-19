package com.github.googelfist.packerhelper.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.PiecesTabsFragmentBinding
import com.github.googelfist.packerhelper.presentation.screens.pieces.PiecesCountingFragment
import com.github.googelfist.packerhelper.presentation.screens.pieces.WeightCountingFragment
import com.google.android.material.tabs.TabLayout

class PiecesTabsFragment : Fragment(R.layout.pieces_tabs_fragment) {

    private var _binding: PiecesTabsFragmentBinding? = null
    private val binding: PiecesTabsFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PiecesTabsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPiecesCountingFragment()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> setPiecesCountingFragment()
                    1 -> setWeightCountingFragment()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    private fun setPiecesCountingFragment() {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.piecesTabsContainer, PiecesCountingFragment.newInstance())
        }
    }

    private fun setWeightCountingFragment() {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.piecesTabsContainer, WeightCountingFragment.newInstance())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}