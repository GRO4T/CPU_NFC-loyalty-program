package com.example.nfcloyaltyprogrammobileapp.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.nfcloyaltyprogrammobileapp.activity.MainActivity
import com.example.nfcloyaltyprogrammobileapp.ui.theme.NFCLoyaltyProgramMobileAppTheme
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel

@Composable
internal fun RegisterRoute() {
    RegisterScreen(MainViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegisterScreen(viewModel: ViewModel) {
    val context = LocalContext.current

    NFCLoyaltyProgramMobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var loginText by remember { mutableStateOf(TextFieldValue("")) }
                TextField(
                    value = loginText,
                    label = { Text(text = "Login") },
                    onValueChange = { it ->
                        loginText = it
                    }
                )
                var passwordText by remember { mutableStateOf(TextFieldValue("")) }
                TextField(
                    value = passwordText,
                    label = { Text(text = "Password") },
                    onValueChange = { it ->
                        passwordText = it
                    }
                )
                Button(onClick = {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }) {
                    Text("Register")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen(MainViewModel())
}