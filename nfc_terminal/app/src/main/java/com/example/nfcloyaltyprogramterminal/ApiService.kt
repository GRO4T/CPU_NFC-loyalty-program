package com.example.nfcloyaltyprogramterminal

import retrofit2.Response
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @PATCH("points/{cardId}")
    suspend fun addPoints(
        @Path("cardId") cardId: String,
        @Body requestBody: RequestBodyPoints
    ): Response<RequestBodyPoints>

    @GET("points/{cardId}")
    suspend fun getCard(
        @Path("cardId") cardId: String
    ): Response<RequestBodyPoints>

    @POST("accounts/{cardId}")
    suspend fun registerCard(
        @Path("cardId") cardId: String
    ): Response<Void>
}
