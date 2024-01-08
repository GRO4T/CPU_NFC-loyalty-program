package com.example.nfcloyaltyprogramterminal

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.nfcloyaltyprogramterminal.network.ApiService
import com.example.nfcloyaltyprogramterminal.ui.theme.NFCLoyaltyProgramTerminalTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : NfcAdapter.ReaderCallback, ComponentActivity() {
    var tagSerialNumber by mutableStateOf("")

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

    private fun registerAccount(cardId: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.registerAccount(cardId)
                Log.d("ApiActivity", "Registered!")
            } catch (e: Exception) {
                Log.e("ApiActivity", "Registration Error", e)
            }
        }
    }
    override fun onTagDiscovered(tag: Tag) {
        tagSerialNumber = tag.id.joinToString(separator = ":") { byte -> "%02x".format(byte) }
        Log.d("NfcComponentActivity", "Discovered tag with serial number: $tagSerialNumber")

        registerAccount(tagSerialNumber)
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
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NFCLoyaltyProgramTerminalTheme {
        Greeting("Android")
    }
}