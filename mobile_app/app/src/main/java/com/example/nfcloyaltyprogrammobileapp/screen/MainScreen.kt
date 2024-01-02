package com.example.nfcloyaltyprogrammobileapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nfcloyaltyprogrammobileapp.ui.theme.NFCLoyaltyProgramMobileAppTheme
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel

@Composable
internal fun MainRoute() {
    MainScreen(MainViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(viewModel: MainViewModel) {
    NFCLoyaltyProgramMobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Text(
                text = "Bring payment card to NFC reader to sign up",
            )
        }
    }
}