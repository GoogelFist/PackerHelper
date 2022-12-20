package com.github.googelfist.packerhelper.presentation.screens.counting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.WeightCountingFragmentBinding
import com.github.googelfist.packerhelper.presentation.screens.InputTextHelper.hideKeyboard
import com.github.googelfist.packerhelper.presentation.screens.counting.model.WeightCountingEvent
import com.github.googelfist.packerhelper.presentation.screens.counting.model.WeightCountingState
import com.google.android.material.textfield.TextInputEditText

class WeightCountingFragment : Fragment(R.layout.weight_counting_fragment) {

    private var _binding: WeightCountingFragmentBinding? = null
    private val binding: WeightCountingFragmentBinding
        get() = _binding!!

    private val viewModel by activityViewModels<WeightCountingViewModel>()

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

        setupButtons()

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WeightCountingState.Counted -> setCountedState(state)
                WeightCountingState.Init -> setInitState()
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
            viewModel.obtainEvent(WeightCountingEvent.ClearFields)
        }
    }

    private fun setupCalculateButton() {
        binding.buttonCalculate.setOnClickListener {

            with(binding) {
                validateEditText(textInputEditTextBoxPieces)
                validateEditText(textInputEditText100PcWeight)

                val boxWeight = textInputEditTextBoxPieces.text.toString()
                val weight100pcs = textInputEditText100PcWeight.text.toString()

                viewModel.obtainEvent(
                    WeightCountingEvent.Calculate(boxWeight.toInt(), weight100pcs.toFloat())
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

    private fun setCountedState(state: WeightCountingState.Counted) {
        binding.tvWeightCounting.text =
            getString(R.string.weight_counting, state.pcs, state.boxWeight)
    }

    private fun setInitState() {
        with(binding) {
            textInputEditTextBoxPieces.setText(EMPTY_STRING)
            textInputEditText100PcWeight.setText(EMPTY_STRING)

            tvWeightCounting.text = EMPTY_STRING
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