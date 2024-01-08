package com.example.nfcloyaltyprogramterminal.network

import com.example.nfcloyaltyprogramterminal.data_structs.StatusResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("accounts/{cardId}")
    suspend fun registerAccount(@Path("cardId") cardId: String): Response<StatusResponse>

    @POST("accounts/{cardId}")
    suspend fun loginAccount(@Path("cardId") cardId: String): Response<StatusResponse>
}
