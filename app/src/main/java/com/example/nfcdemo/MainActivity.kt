package com.example.nfcdemo

import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcdemo.ui.theme.NFCDemoTheme

private var TAG = "MainActivity"

class MainActivity : NfcAdapter.ReaderCallback, ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NFCDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
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
                Log.d(TAG, "text=$text")
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
    NFCDemoTheme {
        Greeting("Android")
    }
}