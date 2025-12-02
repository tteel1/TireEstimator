package com.terrence.tireestimator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.terrence.tireestimator.ui.theme.TireEstimatorTheme
import com.terrence.tireestimator.ui.TireEstimatorScreen
import com.terrence.tireestimator.viewmodel.TireViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.consumeWindowInsets


class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TireEstimatorTheme {
                val viewModel = TireViewModel()
                Scaffold(
                    contentWindowInsets = WindowInsets(0)
                ) { innerPadding ->

                TireEstimatorScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                            .consumeWindowInsets(innerPadding)

                )
                }



            }
        }
    }
}

