package com.softcoreinc.bmicaculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val tag: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun selectUnits(){

        val weightUnits: TextView = findViewById(R.id.weightView)
        val heightUnits: TextView = findViewById(R.id.heightView)
        val metricUnits = findViewById<RadioButton>(R.id.metricButton)

        if (metricUnits.isChecked) {
            weightUnits.append("(kg)")
            heightUnits.append("(m)")
        } else {
            weightUnits.append("(lb)")
            heightUnits.append("(inch)")
        }
    }

    private fun checkUnits(weight: Double, height: Double): Double{
        val unitsGroup: RadioGroup = findViewById(R.id.unitsGroup)
        val selectedUnit = unitsGroup.checkedRadioButtonId
        var result: Double = 0.0

        result = if(selectedUnit == R.id.metricButton){
            calcBMI(weight, height)
        } else {
            calcBmiStandard(weight, height)
        }

        return result

    }

    fun calculateBMI(view: View){
        val weightNumber = findViewById<EditText>(R.id.weightNumber)
        val heightNumber = findViewById<EditText>(R.id.heightNumber)
        var bmiResult = findViewById<EditText>(R.id.bmiResultText)
        var bmiCategory = findViewById<TextView>(R.id.bmi_category_View)
        var bmiCalcResult: Double = 0.0

        try{
            val weight: Double = weightNumber.text.toString().toDouble()
            val height: Double = heightNumber.text.toString().toDouble()


            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            bmiCalcResult = df.format(checkUnits(weight, height)).toDouble()
            bmiResult.setText(bmiCalcResult.toString())

            bmiCategory.text = setBMICategory(bmiCalcResult)

        } catch(e: InputMismatchException) {
            Log.d(tag, "A number was not entered")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(tag, "Something went wrong...")
        }
    }

    private fun calcBMI(weight: Double, height: Double): Double{
        return weight/(height*height)
    }

    private fun calcBmiStandard(weight: Double, height: Double): Double{
        return (703*weight)/(height*height)
    }

    private fun setBMICategory(result: Double): String {
        var category: String = "n/a"

        category = when (result) {
            in 0.0..15.0 -> getString(R.string.very_underweight)
            in 15.1..16.0 -> getString(R.string.severely_underweight)
            in 16.1..18.5 -> getString(R.string.underweight)
            in 18.6..25.0 -> getString(R.string.normal)
            in 25.1..30.0 -> getString(R.string.overweight)
            in 30.1..35.0 -> getString(R.string.obese_class_1)
            in 35.1..40.0 -> getString(R.string.obese_class_2)
            else -> getString(R.string.obese_class_3)
        }

        return category
    }
}