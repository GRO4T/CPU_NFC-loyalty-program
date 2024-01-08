package com.example.nfcloyaltyprogramterminal

import retrofit2.Response
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Body


interface ApiService {
    @PATCH("points/{cardId}")
    suspend fun addPoints(
        @Path("cardId") cardId: String,
        @Body requestBody: RequestBodyPoints
    ): Response<StatusResponse>
}
