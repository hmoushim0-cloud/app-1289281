package com.example.foodapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodRepository
import com.example.foodapp.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {
    val restaurants = repository.getFeaturedRestaurants()
    val cart = repository.cart
    val activeOrder = repository.activeOrder

    fun addToCart(item: MenuItem) = repository.addToCart(item)
    fun removeFromCart(item: MenuItem) = repository.removeFromCart(item)

    fun checkout(restaurant: Restaurant, tip: Double) {
        val order = repository.placeOrder(restaurant, tip)
        // Simulate live delivery progression
        viewModelScope.launch {
            delay(4000)
            // Progress to preparing
        }
    }
}
