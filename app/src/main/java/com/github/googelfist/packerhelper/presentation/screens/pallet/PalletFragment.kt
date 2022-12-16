package com.github.googelfist.packerhelper.presentation.screens.pallet

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.databinding.PalletFragmentBinding
import com.github.googelfist.packerhelper.presentation.screens.InputTextHelper.hideKeyboard
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletEvent
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletState
import com.google.android.material.textfield.TextInputEditText

class PalletFragment : Fragment(R.layout.pallet_fragment) {

    private var _binding: PalletFragmentBinding? = null
    private val binding: PalletFragmentBinding
        get() = _binding!!

    private val viewModel by activityViewModels<PalletViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PalletFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PalletState.Less -> setLessState(state)
                is PalletState.Correct -> setCorrectState(state)
                is PalletState.More -> setMoreState(state)
                PalletState.None -> {}
                is PalletState.InitState -> {
                    binding.textInputEditTextBoxCount.setText(state.boxCount.toString())
                    binding.textInputEditTextPackageWeight.setText(state.packageWeight.toString())
                }
            }
        }

        binding.textInputEditTextBoxWeight.doOnTextChanged { text, start, before, count ->
            val boxCount = if (text.toString().isEmpty() || !text.toString().first().isDigit()) {
                DEFAULT_VALUE
            } else {
                text.toString()
            }
            viewModel.obtainEvent(PalletEvent.CalculateInitParams(boxCount.toFloat()))
        }

        setupButtons()

        binding.root.setOnClickListener {
            it.hideKeyboard()
            requireActivity().currentFocus?.clearFocus()
        }
    }

    private fun setupButtons() {

        binding.buttonCalculate.setOnClickListener {

            with(binding) {
                validateEditText(textInputEditTextBoxWeight)
                validateEditText(textInputEditTextTrayWeight)
                validateEditText(textInputEditTextGrossWeight)
                validateEditText(textInputEditTextBoxCount)
                validateEditText(textInputEditTextPackageWeight)

                val boxWeight = textInputEditTextBoxWeight.text.toString()
                val trayWeight = textInputEditTextTrayWeight.text.toString()
                val grossWeight = textInputEditTextGrossWeight.text.toString()
                val boxCount = textInputEditTextBoxCount.text.toString()
                val packageWeight = textInputEditTextPackageWeight.text.toString()

                viewModel.obtainEvent(
                    PalletEvent.CalculateRealGross(
                        boxWeight = boxWeight.toFloat(),
                        trayWeight = trayWeight.toFloat(),
                        grossWeight = grossWeight.toFloat(),
                        boxCount = boxCount.toInt(),
                        packageWeight = packageWeight.toFloat()
                    )
                )

                it.hideKeyboard()
                requireActivity().currentFocus?.clearFocus()
            }
        }
    }

    private fun validateEditText(editText: TextInputEditText) {
        if (editText.text.toString().isEmpty() || !editText.text.toString().first().isDigit()) {
            editText.setText("0")
        }
    }

    private fun setLessState(state: PalletState.Less) {
        with(binding) {
            tvClearWeight.text = getString(R.string.clear_weight_message, state.clearWeight)
            tvTheoreticalGross.text =
                getString(R.string.theoretical_gross_message, state.theoreticalGross)
            tvAllow.text = getString(R.string.allow_message, state.allow)
            tvRealGross.text =
                getString(R.string.less_real_gross_message, state.realGross, state.diff)

            tvRealGross.setTextColor(Color.RED)
        }

    }

    private fun setCorrectState(state: PalletState.Correct) {
        with(binding) {
            tvClearWeight.text = getString(R.string.clear_weight_message, state.clearWeight)
            tvTheoreticalGross.text =
                getString(R.string.theoretical_gross_message, state.theoreticalGross)
            tvAllow.text = getString(R.string.allow_message, state.allow)
            tvRealGross.text =
                getString(R.string.correct_real_gross_message, state.realGross, state.diff)

            tvRealGross.setTextColor(Color.GREEN)
        }
    }

    private fun setMoreState(state: PalletState.More) {
        with(binding) {
            tvClearWeight.text = getString(R.string.clear_weight_message, state.clearWeight)
            tvTheoreticalGross.text =
                getString(R.string.theoretical_gross_message, state.theoreticalGross)
            tvAllow.text = getString(R.string.allow_message, state.allow)
            tvRealGross.text =
                getString(R.string.more_real_gross_message, state.realGross, state.diff)

            tvRealGross.setTextColor(Color.RED)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_VALUE = "0"
    }
}