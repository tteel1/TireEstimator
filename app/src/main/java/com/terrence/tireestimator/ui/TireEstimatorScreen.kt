package com.terrence.tireestimator.ui



    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import com.terrence.tireestimator.viewmodel.TireViewModel
   // import androidx.compose.material3.TextField
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.tooling.preview.Preview

@Composable
    fun TireEstimatorScreen(viewModel: TireViewModel) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text("Tire Estimator", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            // Inputs
            PriceInput(viewModel)
            QuantityInput(viewModel)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Include TPMS Packs", style = MaterialTheme.typography.bodyMedium)
                Switch(
                    checked = viewModel.updateIncludeTpms,
                    onCheckedChange = { viewModel.setIncludeTpms(it) }
                )
            }

            Spacer(Modifier.height(24.dp))
            Divider()

            // Breakdown
            Text("Cost Breakdown", style = MaterialTheme.typography.titleMedium)
            BreakdownRow("Tire Cost", viewModel.tireCost)
            BreakdownRow("Disposal Fee", viewModel.disposalFee)
            BreakdownRow("TPMS Packs", viewModel.tpmsFeeTotal, )
            BreakdownRow("Tax", viewModel.tax)
            BreakdownRow("State Fee", viewModel.stateFeeTotal)
            BreakdownRow("Subtotal", viewModel.subtotal)
            Divider()
            BreakdownRow("Total", viewModel.totalCost, bold = true)
        }
    }

@Composable
fun PriceInput(viewModel: TireViewModel) {
    TextField(
        value = viewModel.pricePerTire.toString(),
        onValueChange = { viewModel.pricePerTire = it.toDoubleOrNull() ?: 0.0 },
        label = { Text("Price per Tire") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
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

@Preview(showBackground = true)
@Composable
fun TireEstimatorScreenPreview() {
    // You can pass a dummy ViewModel or mock data here
    val mockViewModel = TireViewModel().apply {
        pricePerTire = 274.99
        quantity = 4
        setIncludeTpms(true)

    }

    TireEstimatorScreen(viewModel = mockViewModel)
}
