package com.terrence.tireestimator.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class TireViewModel : ViewModel() {

    var updateIncludeTpms by mutableStateOf(true)
        private set

    fun setIncludeTpms(value: Boolean) {
        updateIncludeTpms = value
    }

    var pricePerTire by mutableStateOf(0.0)
    var quantity by mutableStateOf(4)

    private val stateFee = 2.50
    private val taxRate = 0.08625
    private val tpmsFeePerTire = 2.99
    private val disposalFeePerTire = 2.25

    val tireCost: Double get() = pricePerTire * quantity
    val stateFeeTotal: Double get() = stateFee * quantity
    val tpmsFeeTotal: Double get() = if (updateIncludeTpms) tpmsFeePerTire * quantity else 0.0
    val disposalFee: Double get() = disposalFeePerTire * quantity
    val taxableTotal: Double get() = tireCost + tpmsFeeTotal + disposalFee
    val tax: Double get() = taxableTotal * taxRate
    val subtotal: Double get() = tireCost + tpmsFeeTotal + disposalFee + stateFeeTotal
    val totalCost: Double get() = taxableTotal + tax + stateFeeTotal

    val stateFeePerTire: Double get() = stateFee
    val taxRatePercent: Double get() = taxRate * 100
    val isValid: Boolean get() = pricePerTire >= 0 && quantity > 0
}