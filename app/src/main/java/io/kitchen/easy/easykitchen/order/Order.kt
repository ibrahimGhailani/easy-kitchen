package io.kitchen.easy.easykitchen.order

import io.kitchen.easy.easykitchen.detail.Meal

data class Order(
        val id: Long = System.currentTimeMillis(),
        val meal: Meal,
        val quantity: Int,
        val location: String,
        val day: String,
        val companyId: String
)