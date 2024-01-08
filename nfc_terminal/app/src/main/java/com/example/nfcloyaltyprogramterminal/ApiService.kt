package com.example.nfcloyaltyprogramterminal

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST("accounts/{cardId}")
    suspend fun registerAccount(@Path("cardId") cardId: String): Response<StatusResponse>
}
