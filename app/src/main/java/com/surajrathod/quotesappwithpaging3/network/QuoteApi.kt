package com.surajrathod.quotesappwithpaging3.network

import com.surajrathod.quotesappwithpaging3.model.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("quotes")
    suspend fun getQuote(@Query("page") page : Int) : QuoteList

}