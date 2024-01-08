package com.example.nfcloyaltyprogramterminal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var points by mutableStateOf(0)
        private set

    var status by mutableStateOf("Bring your NFC card to add points.")
        private set

    fun updatePoints(value: Int) {
        points = value
    }

    fun updateStatus(value: String) {
        status = value
    }
}