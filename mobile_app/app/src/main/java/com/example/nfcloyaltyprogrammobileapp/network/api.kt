package com.example.nfcloyaltyprogramterminal.network

import com.example.nfcloyaltyprogramterminal.data_structs.CardPointsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("points/{cardId}")
    suspend fun getCard(
        @Path("cardId") cardId: String
    ): Response<CardPointsResponse>
}
