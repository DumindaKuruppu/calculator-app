package com.example.mycalculatorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var textViewInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDecimalPoint: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewInput = findViewById(R.id.textViewInput)
    }

    fun onDigit(view: View) {
        textViewInput?.append((view as Button).text)
        lastNumeric = true
        lastDecimalPoint = false
    }

    fun onClear(view: View) {
        textViewInput?.text = ""
    }

    fun onDecimalPointClicked(view: View) {
        if (lastNumeric && !lastDecimalPoint) {
            textViewInput?.append(".")
            lastDecimalPoint = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View) {
        textViewInput?.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                textViewInput?.append((view as Button).text)
                lastNumeric = false
                lastDecimalPoint = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var textViewValue = textViewInput?.text.toString()
            var prefix = ""

            try {

                if (textViewValue.startsWith("-")) {
                    prefix = "-"
                    textViewValue = textViewValue.substring(1)
                }

                if (textViewValue.contains("-")) {

                    val splitValue = textViewValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    textViewInput?.text = (one.toDouble() - two.toDouble()).toString()
                }


            } catch (e: ArithmeticException) {
                textViewInput?.text = getString(R.string.syntax_error)
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

}