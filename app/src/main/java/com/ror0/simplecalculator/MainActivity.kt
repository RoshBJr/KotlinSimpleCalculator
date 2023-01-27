package com.ror0.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //properties
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    // method to add numbers to input and execute calculations
    fun onDigit(view: View){ // the view is the button itself and it is called onClick
        //Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
        lastNumeric = true // true because the user pressed a number
        lastDot = false // when dot is equal to false we can add a dot
    }

    // method to Clear input entry, assigned to the CLR Button
    fun onClear(view: View) {
        tvInput?.text = "" // entry is replaced by an empty string
    }

    // method to add decimal
    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    // this method enables us to limit the user to only do one operation at the time example (1 - 2) and not (1 - 2 \ 3)
    fun onOperator(view: View) {
        // first check if the input is different than null if null nothing will be executed else it will execute the code in th scope
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())) { // if the last value was a number and there is not an operator (*,-,+,/) the scope will be executed otherwise not
                tvInput?.append((view as Button).text) // we append the operator to the tvInput
                lastNumeric = false // since the last character is an operator and not an numeric this bool is set to false
                lastDot = false // since we just put an operator before an number we can add a dot for decimals
            }
        }

    }

    // method when the user presses the equal button
    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput?.text.toString() // we catch the operation
            var prefix = ""

            try {
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1) // remove the minus in front of the number
                }
                // Subtraction
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-") // will split the string where the minus is
                    var num1 = splitValue[0]
                    var num2 = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        num1 = prefix + num1 // we reassign the minus
                    }
                    tvInput?.text = removeZeroAfterDot((num1.toDouble() - num2.toDouble()).toString()) // do the calculation

                } else if(tvValue.contains("+")) { // addition
                    val splitValue = tvValue.split("+") // will split the string where the minus is
                    var num1 = splitValue[0]
                    var num2 = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        num1 = prefix + num1 // we reassign the minus
                    }
                    tvInput?.text = removeZeroAfterDot((num1.toDouble() + num2.toDouble()).toString()) // do the calculation

                }else if(tvValue.contains("*")) { // multiplication
                    val splitValue = tvValue.split("*") // will split the string where the minus is
                    var num1 = splitValue[0]
                    var num2 = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        num1 = prefix + num1 // we reassign the minus
                    }
                    tvInput?.text = removeZeroAfterDot((num1.toDouble() * num2.toDouble()).toString()) // do the calculation

                } else if(tvValue.contains("/")) { // division
                    val splitValue = tvValue.split("/") // will split the string where the minus is
                    var num1 = splitValue[0]
                    var num2 = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        num1 = prefix + num1 // we reassign the minus
                    }
                    tvInput?.text = removeZeroAfterDot((num1.toDouble() / num2.toDouble()).toString()) // do the calculation
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    // this method enables us to remove the decimal point when there is no need for precision example (20.0 -> 20)
    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2) // removes the last 2 characters of the string
        }
        return value
    }

    // this methods checks if an operator (*,/,+,-) is present in the tvInput
    // method that takes a string as a parameter and returns a boolean
    private fun isOperatorAdded(value: String): Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/")
            || value.contains("*")
            || value.contains("+")
            || value.contains("-")
        }
    }
}