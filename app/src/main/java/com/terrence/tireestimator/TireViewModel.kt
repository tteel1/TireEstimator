package com.terrence.tireestimator

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TireViewModel : ViewModel() {
    var pricePerTire by mutableStateOf(0.0)
    var quantity by mutableStateOf(4)

    private val stateFee = 2.50
    private val taxRate = 0.08625
    private val tpmsServicePacks = 2.99
    private val disposalFeePerTire = 2.00


    val tireCost: Double get() = pricePerTire * quantity
    val disposalFee: Double get() = disposalFeePerTire * quantity
    val taxableTotal: Double get() = tireCost + tpmsServicePacks + disposalFee
    val tax: Double get() = taxableTotal * taxRate
    val totalCost: Double get() = taxableTotal + tax + stateFee



}

