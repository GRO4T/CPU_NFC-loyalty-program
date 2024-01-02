package com.example.nfcloyaltyprogrammobileapp.activity

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcloyaltyprogrammobileapp.screen.MainRoute
import com.example.nfcloyaltyprogrammobileapp.screen.MainScreen
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel

private var TAG = "MainActivity"

class MainActivity : NfcAdapter.ReaderCallback, ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainRoute()
        }
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

    override fun onTagDiscovered(tag: Tag) {
        val serialNumber = tag.id.joinToString(separator = ":") { byte -> "%02x".format(byte) }
        Log.d(TAG, "Discovered tag with serial number: $serialNumber")
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen(MainViewModel())
}