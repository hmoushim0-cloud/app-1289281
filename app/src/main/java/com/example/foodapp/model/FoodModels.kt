package com.example.foodapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val imageUrl: String,
    val cuisine: String,
    val rating: Double,
    val deliveryTimeMinutes: Int,
    val deliveryFee: Double,
    val menu: List<MenuItem> = emptyList()
)

@Serializable
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val calories: Int = 350
)

data class CartItem(
    val item: MenuItem,
    val quantity: Int = 1,
    val notes: String = ""
)

enum class OrderStatus(val label: String, val stepIndex: Int) {
    PLACED("Order Placed", 0),
    PREPARING("Preparing in Kitchen", 1),
    OUT_FOR_DELIVERY("Out for Delivery", 2),
    DELIVERED("Delivered", 3)
}

data class Order(
    val orderId: String,
    val restaurant: Restaurant,
    val items: List<CartItem>,
    val subtotal: Double,
    val deliveryFee: Double,
    val tip: Double,
    val total: Double,
    val status: OrderStatus = OrderStatus.PLACED,
    val estimatedArrival: String = "25-35 mins"
)
