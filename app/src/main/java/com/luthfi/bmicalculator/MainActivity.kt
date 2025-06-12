package com.luthfi.bmicalculator


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnReset: Button
    private lateinit var tvResult: TextView
    private lateinit var tvCategory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        etWeight = findViewById(R.id.et_weight)
        etHeight = findViewById(R.id.et_height)
        btnCalculate = findViewById(R.id.btn_calculate)
        btnReset = findViewById(R.id.btn_reset)
        tvResult = findViewById(R.id.tv_result)
        tvCategory = findViewById(R.id.tv_category)
    }

    private fun setupClickListeners() {
        btnCalculate.setOnClickListener {
            calculateBMI()
        }

        btnReset.setOnClickListener {
            resetFields()
        }
    }

    private fun calculateBMI() {
        val weightStr = etWeight.text.toString().trim()
        val heightStr = etHeight.text.toString().trim()

        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            showToast("Please fill in all fields!")
            return
        }

        try {
            val weight = weightStr.toDouble()
            val heightCm = heightStr.toDouble()

            if (weight <= 0 || heightCm <= 0) {
                showToast("Weight and height must be greater than 0!")
                return
            }

            // Convert height from cm to meters
            val heightM = heightCm / 100

            // Calculate BMI: weight / (height in meters)^2
            val bmi = weight / heightM.pow(2)

            // Display results
            displayResults(bmi)

        } catch (e: NumberFormatException) {
            showToast("Please enter a valid number!")
        }
    }

    private fun displayResults(bmi: Double) {
        // Format BMI to 1 decimal place
        val bmiFormatted = String.format("%.1f", bmi)
        tvResult.text = "Your BMI: $bmiFormatted"

        // Determine category and color
        val (category, color) = getBMICategory(bmi)

        tvCategory.text = "Category: $category"
        tvCategory.setTextColor(getColor(color))

        // Make result visible (without description)
        tvResult.visibility = TextView.VISIBLE
        tvCategory.visibility = TextView.VISIBLE
    }

    private fun getBMICategory(bmi: Double): Pair<String, Int> {
        if (bmi < 18.5) {
            return Pair("Underweight", android.R.color.holo_blue_dark)
        } else if (bmi >= 18.5 && bmi < 25) {
            return Pair("Normal", android.R.color.holo_green_dark)
        } else if (bmi >= 25 && bmi < 30) {
            return Pair("Overweight", android.R.color.holo_orange_dark)
        } else {
            return Pair("Obese", android.R.color.holo_red_dark)
        }
    }


    private fun resetFields() {
        etWeight.text.clear()
        etHeight.text.clear()
        tvResult.visibility = TextView.GONE
        tvCategory.visibility = TextView.GONE
        etWeight.requestFocus()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}