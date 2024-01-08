package com.example.nfcloyaltyprogramterminal

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.nfcloyaltyprogramterminal.ui.theme.NFCLoyaltyProgramTerminalTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : NfcAdapter.ReaderCallback, ComponentActivity() {
    var tagSerialNumber by mutableStateOf("")
    var viewModel by mutableStateOf(MainViewModel())

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://nfclp.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    override fun onStart() {
        super.onStart()
        NfcAdapter.getDefaultAdapter(this)?.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A,
            null
        )
    }

    override fun onStop() {
        super.onStop()
        NfcAdapter.getDefaultAdapter(this)?.disableReaderMode(this)
    }

    private fun addPoints(cardId: String) { // Will also create card in db if does not exist
        lifecycleScope.launch {
            val cardResponse = apiService.getCard(cardId)
            Log.d("ApiActivity", "Received $cardResponse")

            if (!cardResponse.isSuccessful) {
                Log.i("ApiActivity", "Card $cardId does not exist. Attempting creation")
                val registerResponse = apiService.registerCard(cardId)

                if (!registerResponse.isSuccessful) {
                    Log.e("ApiActivity", "Failed to register card, code ${registerResponse.code()}")
                    viewModel.updateStatus("ERROR: Unable to register card")
                    return@launch
                }
            }
            val response = apiService.addPoints(cardId, RequestBodyPoints(viewModel.points))
            Log.i("ApiActivity", "Added ${viewModel.points} point(s) for card $cardId")
            Log.d("ApiActivity", "Received $response")
            viewModel.updateStatus("${viewModel.points} points have been added to $cardId")
        }
    }

    override fun onTagDiscovered(tag: Tag) {
        tagSerialNumber = tag.id.joinToString(separator = ":") { byte -> "%02x".format(byte) }
        Log.d("NfcComponentActivity", "Discovered tag with serial number: $tagSerialNumber")

        addPoints(tagSerialNumber)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFCLoyaltyProgramTerminalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TerminalScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = modifier,
            value = viewModel.points.toString(),
            onValueChange = { value ->
                if (value.length in 1..3) {
                    viewModel.updatePoints(value.filter { it.isDigit() }.toInt())
                }
            },
            label = { Text("Points") },
        )
        Text(
            viewModel.status
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NFCLoyaltyProgramTerminalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TerminalScreen(MainViewModel())
        }
    }
}