package com.example.nfcloyaltyprogrammobileapp.activity

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.nfcloyaltyprogrammobileapp.screen.PointScreen
import com.example.nfcloyaltyprogrammobileapp.screen.MainScreen
import com.example.nfcloyaltyprogrammobileapp.viewmodel.MainViewModel
import com.example.nfcloyaltyprogramterminal.data_structs.CardPointsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.nfcloyaltyprogramterminal.network.ApiService
import kotlinx.coroutines.GlobalScope
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

    private fun loginAccount(cardId: String) {
        lifecycleScope.launch {
            val response = apiService.getCard(cardId)
            Log.d("ApiActivity", "Got response $response")
            if (response.isSuccessful) {
                val responseBody: CardPointsResponse? = response.body()
                if (responseBody != null) {
                    setContent{PointScreen(MainViewModel(), responseBody.points)}
                    Log.d("ApiActivity", "Points ${responseBody.points}")
                }
            } else {
                Log.e("ApiActivity", "Error getting")
                return@launch
            }
        }
    }


    override fun onTagDiscovered(tag: Tag) {
        tagSerialNumber = tag.id.joinToString(separator = ":") { byte -> "%02x".format(byte) }
        Log.d("NfcComponentActivity", "Discovered tag with serial number: $tagSerialNumber")

        loginAccount(tagSerialNumber)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen(MainViewModel())
}