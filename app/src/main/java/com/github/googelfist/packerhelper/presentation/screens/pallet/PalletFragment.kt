package com.github.googelfist.packerhelper.presentation.screens.pallet

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.googelfist.packerhelper.R
import com.github.googelfist.packerhelper.component
import com.github.googelfist.packerhelper.databinding.PalletFragmentBinding
import com.github.googelfist.packerhelper.presentation.screens.InputTextHelper.hideKeyboard
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletEvent
import com.github.googelfist.packerhelper.presentation.screens.pallet.model.PalletState
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class PalletFragment : Fragment(R.layout.pallet_fragment) {

    private var _binding: PalletFragmentBinding? = null
    private val binding: PalletFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var palletViewModelFactory: PalletViewModelFactory

    private val viewModel by activityViewModels<PalletViewModel> { palletViewModelFactory }

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PalletFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        configEditText()
        setupButtons()
    }

    private fun observeViewModel() {
        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PalletState.InitState -> setInitState(state)
                is PalletState.Less -> setLessState(state)
                is PalletState.Correct -> setCorrectState(state)
                is PalletState.More -> setMoreState(state)
            }
        }
    }

    private fun setInitState(state: PalletState.InitState) {
        val boxCount = validateBoxCount(state.boxCount)
        val packageWeight = validatePackageWeight(state.packageWeight)

        binding.textInputEditTextBoxCount.setText(boxCount)
        binding.textInputEditTextPackageWeight.setText(packageWeight)
    }

    private fun validateBoxCount(boxCount: Int): String {
       return if (boxCount == 0) {
            EMPTY_STRING
        } else {
            boxCount.toString()
        }
    }

    private fun validatePackageWeight(packageWeight: Float): String {
        return if (packageWeight == 0f) {
            EMPTY_STRING
        } else {
            packageWeight.toString()
        }
    }


    private fun setLessState(state: PalletState.Less) {
        with(binding) {
            tvClearWeight.text = getString(R.string.clear_weight_message, state.clearWeight)
            tvTheoreticalGross.text =
                getString(
                    R.string.theoretical_gross_message,
                    state.theoreticalGross,
                    state.limit.minGrossLimit,
                    state.limit.maxGrossLimit
                )
            tvLimit.text = getString(R.string.limit_message, state.limit.value)
            tvRealGross.text =
                getString(
                    R.string.less_real_gross_message,
                    state.realGross,
                    state.limit.minGrossLimit,
                    state.diff
                )

            tvRealGross.setTextColor(Color.RED)
        }
    }

    private fun setCorrectState(state: PalletState.Correct) {
        with(binding) {
            tvClearWeight.text = getString(R.string.clear_weight_message, state.clearWeight)
            tvTheoreticalGross.text =
                getString(
                    R.string.theoretical_gross_message,
                    state.theoreticalGross,
                    state.limit.minGrossLimit,
                    state.limit.maxGrossLimit
                )
            tvLimit.text = getString(R.string.limit_message, state.limit.value)
            tvRealGross.text =
                getString(R.string.correct_real_gross_message, state.realGross, state.diff)

            tvRealGross.setTextColor(Color.BLUE)
        }
    }

    private fun setMoreState(state: PalletState.More) {
        with(binding) {
            tvClearWeight.text = getString(R.string.clear_weight_message, state.clearWeight)
            tvTheoreticalGross.text =
                getString(
                    R.string.theoretical_gross_message,
                    state.theoreticalGross,
                    state.limit.minGrossLimit,
                    state.limit.maxGrossLimit
                )
            tvLimit.text = getString(R.string.limit_message, state.limit.value)
            tvRealGross.text =
                getString(
                    R.string.more_real_gross_message,
                    state.realGross,
                    state.limit.maxGrossLimit,
                    state.diff
                )

            tvRealGross.setTextColor(Color.RED)
        }
    }

    private fun configEditText() {

        binding.textInputEditTextBoxWeight.doOnTextChanged { text, start, before, count ->
            val boxWeight = if (text.toString().isEmpty() || !text.toString().first().isDigit()) {
                DEFAULT_VALUE
            } else {
                text.toString()
            }
            viewModel.obtainEvent(PalletEvent.LoadPallet(boxWeight.toFloat()))
        }

        binding.textInputEditTextPackageWeight.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.textInputEditTextPackageWeight.clearFocus()
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
        with(binding) {

            buttonClear.setOnClickListener {
                textInputEditTextBoxWeight.setText(EMPTY_STRING)
                textInputEditTextTrayWeight.setText(EMPTY_STRING)
                textInputEditTextGrossWeight.setText(EMPTY_STRING)
                textInputEditTextBoxCount.setText(EMPTY_STRING)
                textInputEditTextPackageWeight.setText(EMPTY_STRING)

                tvTheoreticalGross.text = EMPTY_STRING
                tvClearWeight.text = EMPTY_STRING
                tvLimit.text = EMPTY_STRING
                tvRealGross.text = EMPTY_STRING
            }
        }
    }

    private fun setupCalculateButton() {
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

                root.hideKeyboard()
                requireActivity().currentFocus?.clearFocus()
            }
        }
    }

    private fun validateEditText(editText: TextInputEditText) {
        if (editText.text.toString().isEmpty() || !editText.text.toString().first().isDigit()) {
            editText.setText(DEFAULT_VALUE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_VALUE = "0"
        private const val EMPTY_STRING = ""
    }
}