package com.example.nfcdemo

import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import com.example.nfcdemo.ui.theme.NFCDemoTheme

private var TAG = "MainActivity"

class MainActivity : NfcAdapter.ReaderCallback, ComponentActivity() {
    var nfcText: String by mutableStateOf("")
    var nfcTagDiscoveredCount by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NFCDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(nfcText, nfcTagDiscoveredCount)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        NfcAdapter.getDefaultAdapter(this)?.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A
                    or NfcAdapter.FLAG_READER_NFC_B
                    or NfcAdapter.FLAG_READER_NFC_F
                    or NfcAdapter.FLAG_READER_NFC_V
                    or NfcAdapter.FLAG_READER_NFC_BARCODE,
            null
        )
    }

    override fun onStop() {
        super.onStop()
        NfcAdapter.getDefaultAdapter(this)?.disableReaderMode(this)
    }

    override fun onTagDiscovered(tag: Tag) {
        val ndef = Ndef.get(tag)
        val ndefMessage = ndef.cachedNdefMessage
        val records = ndefMessage.records

        for (ndefRecord in records) {
            if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN) {
                val payload = ndefRecord.payload
                val text = payload.copyOfRange(3, payload.size).decodeToString()
                nfcText = text
                nfcTagDiscoveredCount++
                Log.d(TAG, "nfcText=$text")
            }
        }
    }
}

@Composable
fun Greeting(nfcText: String, nfcTagDiscoveredCount: Int, modifier: Modifier = Modifier) {
    Column() {
        Text(
            text = "NFC text: $nfcText",
            modifier = modifier,
            fontSize = 20.sp
        )
        Text(
            text = "NFC tags discovered: $nfcTagDiscoveredCount",
            modifier = modifier,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NFCDemoTheme {
        Greeting("NFC Text", 0)
    }
}