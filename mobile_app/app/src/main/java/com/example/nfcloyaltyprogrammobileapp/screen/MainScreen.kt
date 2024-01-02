package com.example.nfcloyaltyprogrammobileapp.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcloyaltyprogrammobileapp.ui.theme.NFCLoyaltyProgramMobileAppTheme
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel

const val TAG = "MainActivity";

@Composable
internal fun MainRoute() {
    MainScreen(MainViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(viewModel: MainViewModel) {
    NFCLoyaltyProgramMobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(modifier = Modifier.size(300.dp), onClick = {Log.d(TAG, "Sign in")}) {
                    Text(text = "Sign in", fontSize = 50.sp)
                }
                Button(onClick = { Log.d(TAG, "Register account")}) {
                    Text("Register an account")
                }
            }
        }
    }
}