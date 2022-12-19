package com.github.googelfist.packerhelper.presentation.screens.pieces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.WeightCountingFragmentBinding

class WeightCountingFragment: Fragment(R.layout.weight_counting_fragment) {

    private var _binding: WeightCountingFragmentBinding? = null
    private val binding: WeightCountingFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeightCountingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): WeightCountingFragment {
            return WeightCountingFragment()
        }
    }
}