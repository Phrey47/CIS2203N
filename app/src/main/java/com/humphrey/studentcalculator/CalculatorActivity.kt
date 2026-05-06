package com.humphrey.studentcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstOperand = 0.0

    companion object {
        private const val KEY_INPUT = "currentInput"
        private const val KEY_OPERATOR = "operator"
        private const val KEY_FIRST_OPERAND = "firstOperand"
        private const val KEY_DISPLAY = "display"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        tvDisplay = findViewById(R.id.tvDisplay)

        savedInstanceState?.let {
            currentInput = it.getString(KEY_INPUT, "")
            operator = it.getString(KEY_OPERATOR, "")
            firstOperand = it.getDouble(KEY_FIRST_OPERAND, 0.0)
            tvDisplay.text = it.getString(KEY_DISPLAY, "0")
        }

        val calculatorListener = { v: android.view.View ->
            val button = v as Button
            val buttonText = button.text.toString()

            when (v.id) {
                R.id.btnClear -> {
                    currentInput = ""
                    operator = ""
                    firstOperand = 0.0
                    tvDisplay.text = "0"
                }
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide -> {
                    if (currentInput.isNotEmpty()) {
                        firstOperand = currentInput.toDouble()
                        operator = buttonText
                        currentInput = ""
                    }
                }
                R.id.btnEquals -> {
                    if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
                        val secondOperand = currentInput.toDouble()
                        val result = calculateResult(firstOperand, secondOperand, operator)
                        tvDisplay.text = if (result == result.toLong().toDouble()) {
                            result.toLong().toString()
                        } else {
                            result.toString()
                        }
                        currentInput = tvDisplay.text.toString()
                        operator = ""
                    }
                }
                else -> {
                    currentInput += buttonText
                    tvDisplay.text = currentInput
                }
            }
        }

        val buttonIds = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnClear, R.id.btnEquals
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(calculatorListener)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_INPUT, currentInput)
        outState.putString(KEY_OPERATOR, operator)
        outState.putDouble(KEY_FIRST_OPERAND, firstOperand)
        outState.putString(KEY_DISPLAY, tvDisplay.text.toString())
    }

    private fun calculateResult(num1: Double, num2: Double, op: String): Double {
        return when (op) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 == 0.0) 0.0 else num1 / num2
            else -> 0.0
        }
    }
}