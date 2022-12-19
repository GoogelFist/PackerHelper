package com.github.googelfist.packerhelper.presentation.screens.pieces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.PiecesCountingFragmentBinding

class PiecesCountingFragment: Fragment(R.layout.pieces_counting_fragment) {

    private var _binding: PiecesCountingFragmentBinding? = null
    private val binding: PiecesCountingFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PiecesCountingFragmentBinding.inflate(inflater, container, false)
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
        fun newInstance(): PiecesCountingFragment {
            return PiecesCountingFragment()
        }
    }
}