package com.example.foodapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodapp.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodHomeScreen(
    viewModel: FoodViewModel,
    onRestaurantClick: (Restaurant) -> Unit,
    onCartClick: () -> Unit
) {
    val cart by viewModel.cart.collectAsState()
    val totalCartItems = cart.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Deliver to 📍", fontSize = 12.sp, color = Color.Gray)
                        Text("Home (King Fahd Rd)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        BadgedBox(badge = {
                            if (totalCartItems > 0) {
                                Badge { Text("$totalCartItems") }
                            }
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Category Chips
                val categories = listOf("🔥 Popular", "🍔 Burgers", "🍕 Pizza", "🥗 Healthy", "🍣 Sushi", "☕ Coffee")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = cat.contains("Popular"),
                            onClick = {},
                            label = { Text(cat) }
                        )
                    }
                }
            }

            item {
                Text("Featured Restaurants", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            items(viewModel.restaurants) { restaurant ->
                RestaurantCard(restaurant = restaurant, onClick = { onRestaurantClick(restaurant) })
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = restaurant.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(restaurant.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE8F5E9)
                    ) {
                        Text(
                            "★ ${restaurant.rating}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(restaurant.cuisine, color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("⏱ ${restaurant.deliveryTimeMinutes} mins", fontSize = 13.sp)
                    Text("🛵 Delivery: $${restaurant.deliveryFee}", fontSize = 13.sp)
                }
            }
        }
    }
}
