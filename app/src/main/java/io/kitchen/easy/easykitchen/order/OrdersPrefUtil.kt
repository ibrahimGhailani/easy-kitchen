package io.kitchen.easy.easykitchen.order

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun hasPreviousOrders(context: Context): Boolean {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val orders = sharedPreferences.getString(ORDERS, "")
    return orders.isNotEmpty()
}

fun saveOrder(context: Context, order: Order) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val gson = Gson()
    val ordersString = sharedPreferences.getString(ORDERS, "")


    val orders = if (ordersString.isNotEmpty()) {
        gson.fromJson<List<Order>>(ordersString, type).toMutableList()
    } else {
        mutableListOf()
    }
    orders.add(order)
    sharedPreferences.edit()
            .putString(ORDERS, Gson().toJson(orders))
            .apply()
}


fun getOrders(context: Context): List<Order> {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val gson = Gson()
    val ordersString = sharedPreferences.getString(ORDERS, "")
    return if (ordersString.isNotEmpty()) {
        gson.fromJson<List<Order>>(ordersString, type).toMutableList()
    } else {
        mutableListOf()
    }
}

const val ORDERS = "orders"
val type = object : TypeToken<List<Order>>() {}.type!!


