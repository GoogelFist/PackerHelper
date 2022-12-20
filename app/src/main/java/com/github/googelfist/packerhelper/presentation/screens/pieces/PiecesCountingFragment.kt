package com.github.googelfist.packerhelper.presentation.screens.pieces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.PiecesCountingFragmentBinding
import com.github.googelfist.packerhelper.presentation.screens.InputTextHelper.hideKeyboard
import com.github.googelfist.packerhelper.presentation.screens.pieces.model.PiecesCountingEvent
import com.github.googelfist.packerhelper.presentation.screens.pieces.model.PiecesCountingState
import com.google.android.material.textfield.TextInputEditText

class PiecesCountingFragment : Fragment(R.layout.pieces_counting_fragment) {

    private var _binding: PiecesCountingFragmentBinding? = null
    private val binding: PiecesCountingFragmentBinding
        get() = _binding!!

    private val viewModel by activityViewModels<PiecesCountingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PiecesCountingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PiecesCountingState.Counted -> setCountedState(state)
                PiecesCountingState.Init -> setInitState()
            }
        }

        binding.textInputEditText100PcWeight.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.textInputEditText100PcWeight.clearFocus()
                v.hideKeyboard()
            }
            false
        }
    }

    private fun setupButtons() {

        binding.root.setOnClickListener {
            it.hideKeyboard()
            requireActivity().currentFocus?.clearFocus()
        }

        setupCalculateButton()

        setupClearButton()
    }

    private fun setupClearButton() {
        binding.buttonClear.setOnClickListener {
            viewModel.obtainEvent(PiecesCountingEvent.ClearFields)
        }
    }

    private fun setupCalculateButton() {
        binding.buttonCalculate.setOnClickListener {

            with(binding) {
                validateEditText(textInputEditTextBoxWeight)
                validateEditText(textInputEditText100PcWeight)

                val boxWeight = textInputEditTextBoxWeight.text.toString()
                val weight100pcs = textInputEditText100PcWeight.text.toString()

                viewModel.obtainEvent(
                    PiecesCountingEvent.Calculate(boxWeight.toFloat(), weight100pcs.toFloat())
                )

                root.hideKeyboard()
                requireActivity().currentFocus?.clearFocus()
            }
        }
    }

    private fun validateEditText(editText: TextInputEditText) {
        val text = editText.text.toString()
        when {
            text.isEmpty() -> editText.setText(DEFAULT_VALUE)
            text.startsWith(DOT) -> editText.setText(getString(R.string.et_starts_with_dot, text))
            !text.first().isDigit() -> editText.setText(DEFAULT_VALUE)
        }
    }

    private fun setCountedState(state: PiecesCountingState.Counted) {
        binding.tvPcsCounting.text = getString(R.string.pieces_counting, state.boxWeight, state.pcs)
    }

    private fun setInitState() {
        with(binding) {
            textInputEditTextBoxWeight.setText(EMPTY_STRING)
            textInputEditText100PcWeight.setText(EMPTY_STRING)

            tvPcsCounting.text = EMPTY_STRING
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DOT = '.'
        private const val DEFAULT_VALUE = "0"
        private const val EMPTY_STRING = ""
    }
}