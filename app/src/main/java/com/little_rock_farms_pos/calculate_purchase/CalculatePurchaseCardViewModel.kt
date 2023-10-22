package com.little_rock_farms_pos.calculate_purchase
data class CalculatePurchaseCardViewModel(
    val category: String,
    val product: String,
    var price: Float,
    var price_string: String,
    var quantity: String,
    var subtotal: Float,
    var subtotal_string: String
)
