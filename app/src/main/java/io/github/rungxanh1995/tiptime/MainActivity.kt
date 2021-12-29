package io.github.rungxanh1995.tiptime

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.github.rungxanh1995.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.costOfServiceEditText.setOnKeyListener      { v, keyCode, _ -> handleKeyEvent(v, keyCode) }

        binding.calculateButton.setOnClickListener          { calculateTip() }
        binding.tipOptions.setOnCheckedChangeListener       { _, _ -> calculateTip() }
        binding.roundUpSwitch.setOnCheckedChangeListener    { _, _ -> calculateTip() }
    }


    @SuppressLint("SetTextI18n")
    private fun calculateTip() {
        val subtotal                = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (subtotal == null) {
            binding.tipResult.text  = currencyFormatNumber(0.0)
            return
        }

        val percentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_20_percent  -> 0.2
            R.id.option_18_percent  -> 0.18
            else                    -> 0.15
        }
        var tipAmount               = subtotal.times(percentage)
        if (binding.roundUpSwitch.isChecked) { tipAmount = kotlin.math.ceil(tipAmount) }

        val totalAmount             = subtotal.plus(tipAmount)

        binding.tipResult.text      = getString(R.string.tip_amount, currencyFormatNumber(tipAmount))
        binding.totalResult.text    = getString(R.string.total_amount, currencyFormatNumber(totalAmount))
    }


    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }


    private fun currencyFormatNumber(number: Double): String {
        return NumberFormat.getCurrencyInstance().format(number)
    }
}