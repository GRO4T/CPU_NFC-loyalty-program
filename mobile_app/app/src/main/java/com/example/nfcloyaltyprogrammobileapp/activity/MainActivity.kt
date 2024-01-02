package com.example.nfcloyaltyprogrammobileapp.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcloyaltyprogrammobileapp.screen.MainRoute
import com.example.nfcloyaltyprogrammobileapp.screen.MainScreen
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel

private var TAG = "MainActivity"

class MainActivity : NfcComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainRoute()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen(MainViewModel())
}