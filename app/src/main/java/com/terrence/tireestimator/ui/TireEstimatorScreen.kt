package com.terrence.tireestimator.ui



    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import com.terrence.tireestimator.viewmodel.TireViewModel
   // import androidx.compose.material3.TextField
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.tooling.preview.Preview
    import com.terrence.tireestimator.viewmodel.TirePromotion

@Composable
    fun TireEstimatorScreen(viewModel: TireViewModel,modifier: Modifier = Modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)) {

            Text("", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Staggered Setup", style = MaterialTheme.typography.bodyMedium)
                Switch(
                    checked = viewModel.isStaggered,
                    onCheckedChange = { viewModel.isStaggered = it }
                )
            }

            // Inputs
            if (viewModel.isStaggered) {
                StaggeredInputs(viewModel)
            } else {
                PriceInput(viewModel)
                QuantityInput(viewModel)
            }
            PromotionSelector(viewModel)


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Include TPMS Packs", style = MaterialTheme.typography.bodyMedium)
                Switch(
                    checked = viewModel.includeTpms,
                    onCheckedChange = { viewModel.updateIncludeTpms(it) }
                )
            }

            Spacer(Modifier.height(24.dp))
            Divider()

            // Breakdown
            Text("Cost Breakdown", style = MaterialTheme.typography.titleMedium)
            BreakdownRow("Tire Cost", viewModel.tireCost)
            BreakdownRow("Disposal Fee", viewModel.disposalFee)
            BreakdownRow("TPMS Packs", viewModel.tpmsFeeTotal )
            BreakdownRow("Promotion Discount", -viewModel.promotionDiscount)
            BreakdownRow("Tax", viewModel.tax)
            BreakdownRow("State Fee", viewModel.stateFeeTotal)
            BreakdownRow("Subtotal", viewModel.subtotal)
            Divider()
            BreakdownRow("Total", viewModel.totalCost, bold = true)
        }
    }



@Composable
fun PriceInput(viewModel: TireViewModel) {
    val formatted = "$" + String.format("%.2f", viewModel.pricePerTire)
    TextField(
        value = formatted,
        onValueChange = { input ->
            viewModel.squareRawInput = input.filter { it.isDigit() }
        },
        label = { Text("Price per Tire") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun StaggeredInputs(viewModel: TireViewModel) {
    if (viewModel.isStaggered) {
        val frontFormatted = "$" + String.format("%.2f", viewModel.frontPricePerTire)
        TextField(
            value = frontFormatted,
            onValueChange = { input ->
                viewModel.frontRawInput = input.filter { it.isDigit() }
            },
            label = { Text("Front Tire Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.frontQuantity.toString(),
            onValueChange = { viewModel.frontQuantity = it.toIntOrNull() ?: 0 },
            label = { Text("Front Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        val rearFormatted = "$" + String.format("%.2f", viewModel.rearPricePerTire)
        TextField(
            value = rearFormatted,
            onValueChange = { input ->
                viewModel.rearRawInput = input.filter { it.isDigit() }
            },
            label = { Text("Rear Tire Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.rearQuantity.toString(),
            onValueChange = { viewModel.rearQuantity = it.toIntOrNull() ?: 0 },
            label = { Text("Rear Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun QuantityInput(viewModel: TireViewModel) {
    TextField(
        value = viewModel.quantity.toString(),
        onValueChange = { viewModel.quantity = it.toIntOrNull() ?: 0 },
        label = { Text("Quantity") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BreakdownRow(label: String, amount: Double, bold: Boolean = false) {
    val style = if (bold) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = style)
        Text("$${"%.2f".format(amount)}", style = style)

    }
}

@Composable
fun PromotionSelector(viewModel: TireViewModel) {
    Column {
        Text("Promotion", style = MaterialTheme.typography.bodyMedium)
        Row {
            TirePromotion.values().forEach { promo ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.selectedPromotion == promo,
                        onClick = { viewModel.setPromotion(promo) }
                    )
                    Text(text = when (promo) {
                        TirePromotion.NONE -> "No Promo"
                        TirePromotion.OFF_60 -> "$60 Off"
                        TirePromotion.OFF_80 -> "$80 Off"
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TireEstimatorScreenPreview() {
    // You can pass a dummy ViewModel or mock data here
    val mockViewModel = TireViewModel().apply {
        squareRawInput = "27499"
        quantity = 4
        updateIncludeTpms(true)

    }

    TireEstimatorScreen(viewModel = mockViewModel)
}
