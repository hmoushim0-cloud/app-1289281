package com.example.foodapp.di

import com.example.foodapp.data.FoodRepository
import com.example.foodapp.ui.screens.FoodViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FoodRepository() }
    viewModel { FoodViewModel(get()) }
}
