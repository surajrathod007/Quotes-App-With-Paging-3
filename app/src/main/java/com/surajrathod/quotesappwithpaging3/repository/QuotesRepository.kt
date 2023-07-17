package com.surajrathod.quotesappwithpaging3.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.surajrathod.quotesappwithpaging3.network.QuoteApi
import com.surajrathod.quotesappwithpaging3.paging.QuotesPagingSource
import com.surajrathod.quotesappwithpaging3.paging.QuotesRemoteMediator
import com.surajrathod.quotesappwithpaging3.room.QuotesDatabase
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quotesDatabase: QuotesDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getQuotes() =
        Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            remoteMediator = QuotesRemoteMediator(quoteApi, quotesDatabase),
            pagingSourceFactory = {
                //QuotesPagingSource(quoteApi)
                quotesDatabase.quoteDao().getQuote()
            }).liveData

}