package com.terrence.tireestimator.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.math.BigDecimal
import java.math.RoundingMode



enum class TirePromotion(val discountAmount: Double) {
    NONE(0.0),
    OFF_60(60.0),
    OFF_80(80.0)
}

class TireViewModel : ViewModel() {



    var selectedPromotion by mutableStateOf(TirePromotion.NONE)
        private set

    fun setPromotion(promo: TirePromotion) {
        selectedPromotion = promo
    }



    fun updateIncludeTpms(value: Boolean) {
        includeTpms = value
    }
    var isStaggered by mutableStateOf(false)

    var includeTpms by mutableStateOf(true)
        private set






    var squareRawInput by mutableStateOf("")
    var frontRawInput by mutableStateOf("")
    var rearRawInput by mutableStateOf("")

    var frontQuantity by mutableStateOf(2)
    var rearQuantity by mutableStateOf(2)
    var quantity by mutableStateOf(4)


    val pricePerTire: Double
        get() = (squareRawInput.toLongOrNull() ?: 0L) / 100.0

    val frontPricePerTire: Double
        get() = (frontRawInput.toLongOrNull() ?: 0L) / 100.0

    val rearPricePerTire: Double
        get() = (rearRawInput.toLongOrNull() ?: 0L) / 100.0


    private val stateFee = 2.50
    private val taxRate = 0.0875
    private val tpmsFeePerTire = 2.99
    private val disposalFeePerTire = 2.25

    val tireCost: Double get() =
        if (isStaggered) {
            (frontPricePerTire * frontQuantity) +
                    (rearPricePerTire * rearQuantity)
        } else {
            pricePerTire * quantity
        }

    val totalQuantity: Int get() =
        if (isStaggered) frontQuantity + rearQuantity else quantity




    val stateFeeTotal: Double get() = stateFee * totalQuantity
    val tpmsFeeTotal: Double get() = if (includeTpms) tpmsFeePerTire * totalQuantity else 0.0
    val disposalFee: Double get() = disposalFeePerTire * totalQuantity

    val taxableTotal: Double get() = tireCost + tpmsFeeTotal + disposalFee
    val tax: Double get() =  BigDecimal(taxableTotal * taxRate)
        .setScale(2, RoundingMode.DOWN)
        .toDouble()

    val promotionDiscount: Double
        get() = if (totalQuantity >= 4) selectedPromotion.discountAmount else 0.0

    val subtotal: Double get() = tireCost + tpmsFeeTotal + disposalFee + stateFeeTotal
    val totalCost: Double get() = BigDecimal(taxableTotal + tax + stateFeeTotal - promotionDiscount)
        .setScale(2, RoundingMode.DOWN)
        .toDouble()

    val stateFeePerTire: Double get() = stateFee
    val taxRatePercent: Double get() = taxRate * 100
    val isValid: Boolean get() =
        if (isStaggered) {
            frontPricePerTire >= 0 &&
                    rearPricePerTire >= 0 &&
                    frontQuantity > 0 && rearQuantity > 0
        } else {
            pricePerTire >= 0 && quantity > 0
        }
}