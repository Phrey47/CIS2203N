package com.humphrey.kotlincalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalculatorActivity : AppCompatActivity() {

    var currentInput = ""
    var firstOperand = 0.0
    var currentOperator = ""

    fun addNumbers(a: Double, b: Double): Double {
        return a + b
    }

    fun parseInput(): Double {
        val number = currentInput.toDoubleOrNull() ?: 0.0
        return number
    }

    fun calculate(a: Double, b: Double): Double {
        return when (currentOperator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else 0.0
            else -> 0.0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // View references
        val tvDisplay = findViewById<TextView>(R.id.tv_display)
        val btn0 = findViewById<Button>(R.id.btn0)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnEquals = findViewById<Button>(R.id.btnEquals)

        // Digit listeners
        val digitListener = { btn: Button ->
            btn.setOnClickListener {
                currentInput += btn.text
                tvDisplay.text = currentInput
            }
        }

        digitListener(btn0)
        digitListener(btn1)
        digitListener(btn2)
        digitListener(btn3)
        digitListener(btn4)
        digitListener(btn5)
        digitListener(btn6)
        digitListener(btn7)
        digitListener(btn8)
        digitListener(btn9)

        // Operator listeners
        val operatorListener = { btn: Button ->
            btn.setOnClickListener {
                firstOperand = parseInput()
                currentOperator = btn.text.toString()
                currentInput = ""
            }
        }

        operatorListener(btnAdd)
        operatorListener(btnSubtract)
        operatorListener(btnMultiply)
        operatorListener(btnDivide)

        // Equals listener
        btnEquals.setOnClickListener {
            val secondOperand = parseInput()
            val result = calculate(firstOperand, secondOperand)
            tvDisplay.text = result.toString()
            currentInput = result.toString()
            currentOperator = ""
        }
    }
}