package com.surajrathod.quotesappwithpaging3.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.surajrathod.quotesappwithpaging3.network.QuoteApi
import com.surajrathod.quotesappwithpaging3.paging.QuotesPagingSource
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val quoteApi: QuoteApi) {

    fun getQuotes() =
        Pager(config = PagingConfig(pageSize = 20, maxSize = 100), pagingSourceFactory = {
            QuotesPagingSource(quoteApi)
        }).liveData

}