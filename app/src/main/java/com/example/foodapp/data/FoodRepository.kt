package com.example.foodapp.data

import com.example.foodapp.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FoodRepository {
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    private val _activeOrder = MutableStateFlow<Order?>(null)
    val activeOrder: StateFlow<Order?> = _activeOrder.asStateFlow()

    fun getFeaturedRestaurants(): List<Restaurant> = listOf(
        Restaurant(
            id = "r1",
            name = "Burger Bistro & Grill",
            imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd",
            cuisine = "Burgers • American",
            rating = 4.8,
            deliveryTimeMinutes = 20,
            deliveryFee = 2.50,
            menu = listOf(
                MenuItem("m1", "Truffle Smash Burger", "Double wagyu patty, black truffle aioli, aged cheddar.", 14.50, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd", "Burgers"),
                MenuItem("m2", "Crispy Truffle Fries", "Hand-cut Idaho fries with rosemary and parmesan.", 5.50, "https://images.unsplash.com/photo-1573080496219-bb080dd4f877", "Sides")
            )
        ),
        Restaurant(
            id = "r2",
            name = "Napoletana Artisan Pizza",
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591",
            cuisine = "Italian • Wood-fired Pizza",
            rating = 4.9,
            deliveryTimeMinutes = 30,
            deliveryFee = 1.99,
            menu = listOf(
                MenuItem("m3", "Margherita D.O.P.", "San Marzano tomatoes, buffalo mozzarella, fresh basil.", 16.00, "https://images.unsplash.com/photo-1513104890138-7c749659a591", "Pizza"),
                MenuItem("m4", "Diavola Spicy Salami", "Spicy Calabrian salami, chili oil, smoked provolone.", 18.00, "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee", "Pizza")
            )
        )
    )

    fun addToCart(item: MenuItem) {
        val current = _cart.value.toMutableList()
        val existingIndex = current.indexOfFirst { it.item.id == item.id }
        if (existingIndex >= 0) {
            val existing = current[existingIndex]
            current[existingIndex] = existing.copy(quantity = existing.quantity + 1)
        } else {
            current.add(CartItem(item, 1))
        }
        _cart.value = current
    }

    fun removeFromCart(item: MenuItem) {
        val current = _cart.value.toMutableList()
        val existingIndex = current.indexOfFirst { it.item.id == item.id }
        if (existingIndex >= 0) {
            val existing = current[existingIndex]
            if (existing.quantity > 1) {
                current[existingIndex] = existing.copy(quantity = existing.quantity - 1)
            } else {
                current.removeAt(existingIndex)
            }
            _cart.value = current
        }
    }

    fun placeOrder(restaurant: Restaurant, tip: Double): Order {
        val subtotal = _cart.value.sumOf { it.item.price * it.quantity }
        val order = Order(
            orderId = "ORD-${System.currentTimeMillis() % 10000}",
            restaurant = restaurant,
            items = _cart.value,
            subtotal = subtotal,
            deliveryFee = restaurant.deliveryFee,
            tip = tip,
            total = subtotal + restaurant.deliveryFee + tip,
            status = OrderStatus.PLACED
        )
        _activeOrder.value = order
        _cart.value = emptyList()
        return order
    }
}
