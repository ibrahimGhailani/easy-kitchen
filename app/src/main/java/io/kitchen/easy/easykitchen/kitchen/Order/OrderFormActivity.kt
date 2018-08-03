package io.kitchen.easy.easykitchen.kitchen.Order

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import io.kitchen.easy.easykitchen.R
import io.kitchen.easy.easykitchen.detail.Meal
import kotlinx.android.synthetic.main.activity_order_form.*

class OrderFormActivity : AppCompatActivity() {

    lateinit var meal:Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_form)

        setSupportActionBar(appBar)

        meal = intent.getParcelableExtra("meal")
        title = meal.name

        val areasAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,resources.getStringArray(R.array.areas))
        area.adapter = areasAdapter

        val daysAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,resources.getStringArray(R.array.days))
        days.adapter = daysAdapter
    }
}
