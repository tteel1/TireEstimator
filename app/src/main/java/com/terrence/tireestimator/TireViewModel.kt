package com.terrence.tireestimator

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TireViewModel : ViewModel() {
    var pricePerTire by mutableStateOf(0.0)
    var quantity by mutableStateOf(4)
    var taxRate by mutableStateOf(0.08625)
}