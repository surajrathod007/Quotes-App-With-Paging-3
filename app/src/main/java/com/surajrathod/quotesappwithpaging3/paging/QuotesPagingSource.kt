package com.surajrathod.quotesappwithpaging3.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.surajrathod.quotesappwithpaging3.model.Result
import com.surajrathod.quotesappwithpaging3.network.QuoteApi
import java.lang.Exception

class QuotesPagingSource(val quotesApi: QuoteApi) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val position = params.key ?: 1
            val r = quotesApi.getQuote(position)
            LoadResult.Page(
                data = r.results,
                nextKey = if (position == r.totalPages) null else position + 1,
                prevKey = if (position == 1) null else position - 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}