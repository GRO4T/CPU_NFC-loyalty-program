package com.example.nfcloyaltyprogrammobileapp.activity

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class NfcComponentActivity : NfcAdapter.ReaderCallback, ComponentActivity() {
    var tagSerialNumber by mutableStateOf("")

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
        tagSerialNumber = tag.id.joinToString(separator = ":") { byte -> "%02x".format(byte) }
        Log.d("NfcComponentActivity", "Discovered tag with serial number: $tagSerialNumber")
    }
}