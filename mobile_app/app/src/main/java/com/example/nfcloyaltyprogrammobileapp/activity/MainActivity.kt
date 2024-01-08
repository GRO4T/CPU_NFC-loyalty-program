package com.example.nfcloyaltyprogrammobileapp.activity

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.nfcloyaltyprogrammobileapp.screen.LoginScreen
import com.example.nfcloyaltyprogrammobileapp.screen.MainScreen
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.nfcloyaltyprogramterminal.network.ApiService
import kotlinx.coroutines.launch

class MainActivity : NfcComponentActivity() {

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://nfclp.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(MainViewModel())
        }
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

    private fun loginAccount(cardId: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.loginAccount(cardId)
                Log.d("ApiActivity", "Registered!")
            } catch (e: Exception) {
                Log.e("ApiActivity", "Registration Error", e)
            }
        }
    }

    override fun onTagDiscovered(tag: Tag) {
        tagSerialNumber = tag.id.joinToString(separator = ":") { byte -> "%02x".format(byte) }
        Log.d("NfcComponentActivity", "Discovered tag with serial number: $tagSerialNumber")
        var points = 500
        //points = loginAccount(tagSerialNumber)
        setContent{LoginScreen(MainViewModel(), points)}
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen(MainViewModel())
}