package com.example.nfcloyaltyprogrammobileapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.nfcloyaltyprogrammobileapp.ui.theme.NFCLoyaltyProgramMobileAppTheme
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(viewModel: ViewModel, points: Int) {
    val context = LocalContext.current

    NFCLoyaltyProgramMobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("You have $points points")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen(MainViewModel(), 0)
}