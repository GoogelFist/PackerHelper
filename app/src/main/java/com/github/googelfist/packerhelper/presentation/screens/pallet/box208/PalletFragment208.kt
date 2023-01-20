package com.github.googelfist.packerhelper.presentation.screens.pallet.box208

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
import com.github.googelfist.packerhelper.databinding.PalletFragment208Binding
import com.github.googelfist.packerhelper.presentation.screens.InputTextHelper.hideKeyboard
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model.PalletEvent208
import com.github.googelfist.packerhelper.presentation.screens.pallet.box208.model.PalletState208
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class PalletFragment208 : Fragment(R.layout.pallet_fragment_208) {

    private var _binding: PalletFragment208Binding? = null
    private val binding: PalletFragment208Binding
        get() = _binding!!

    @Inject
    lateinit var palletViewModelFactory: Pallet208ViewModelFactory

    private val viewModel by activityViewModels<Pallet208ViewModel> { palletViewModelFactory }

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PalletFragment208Binding.inflate(inflater, container, false)
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
                is PalletState208.InitState -> setInitState(state)
                is PalletState208.Less -> setLessState(state)
                is PalletState208.Correct -> setCorrectState(state)
                is PalletState208.More -> setMoreState(state)
                PalletState208.ClearState -> setClearState()
            }
        }
    }

    private fun setInitState(state: PalletState208.InitState) {
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


    private fun setLessState(state: PalletState208.Less) {
        with(binding) {
            tvClearWeight.text =
                getString(R.string.clear_weight_message, state.clearWeight, state.limit208.value)
            tvTheoreticalGross.text =
                getString(
                    R.string.theoretical_gross_message,
                    state.theoreticalGross,
                    state.limit208.minGrossLimit,
                    state.limit208.maxGrossLimit
                )
            tvRealGross.text =
                getString(
                    R.string.less_real_gross_message,
                    state.realGross,
                    state.limit208.minGrossLimit,
                    state.diff
                )

            tvRealGross.setTextColor(Color.RED)
        }
    }

    private fun setCorrectState(state: PalletState208.Correct) {
        with(binding) {
            tvClearWeight.text =
                getString(R.string.clear_weight_message, state.clearWeight, state.limit208.value)
            tvTheoreticalGross.text =
                getString(
                    R.string.theoretical_gross_message,
                    state.theoreticalGross,
                    state.limit208.minGrossLimit,
                    state.limit208.maxGrossLimit
                )
            tvRealGross.text =
                getString(R.string.correct_real_gross_message, state.realGross, state.diff)

            tvRealGross.setTextColor(Color.BLUE)
        }
    }

    private fun setMoreState(state: PalletState208.More) {
        with(binding) {
            tvClearWeight.text =
                getString(R.string.clear_weight_message, state.clearWeight, state.limit208.value)
            tvTheoreticalGross.text =
                getString(
                    R.string.theoretical_gross_message,
                    state.theoreticalGross,
                    state.limit208.minGrossLimit,
                    state.limit208.maxGrossLimit
                )
            tvRealGross.text =
                getString(
                    R.string.more_real_gross_message,
                    state.realGross,
                    state.limit208.maxGrossLimit,
                    state.diff
                )

            tvRealGross.setTextColor(Color.RED)
        }
    }

    private fun setClearState() {
        with(binding) {
            textInputEditTextBoxWeight.setText(EMPTY_STRING)
            textInputEditTextTrayWeight.setText(EMPTY_STRING)
            textInputEditTextGrossWeight.setText(EMPTY_STRING)
            textInputEditTextBoxCount.setText(EMPTY_STRING)
            textInputEditTextPackageWeight.setText(EMPTY_STRING)

            tvTheoreticalGross.text = EMPTY_STRING
            tvClearWeight.text = EMPTY_STRING
            tvRealGross.text = EMPTY_STRING
        }
    }

    private fun configEditText() {

        binding.textInputEditTextBoxWeight.doOnTextChanged { text, start, before, count ->
            val boxWeight = if (text.toString().isEmpty() || !text.toString().first().isDigit()) {
                DEFAULT_VALUE
            } else {
                text.toString()
            }
            viewModel.obtainEvent(PalletEvent208.LoadPallet(boxWeight.toFloat()))
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
        binding.buttonClear.setOnClickListener {
            viewModel.obtainEvent(PalletEvent208.ClearFields)
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
                    PalletEvent208.CalculateRealGross(
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

        fun newInstance(): PalletFragment208 {
            return PalletFragment208()
        }
    }
}