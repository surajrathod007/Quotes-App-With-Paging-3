package com.surajrathod.quotesappwithpaging3.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.surajrathod.quotesappwithpaging3.model.QuoteRemoteKeys
import com.surajrathod.quotesappwithpaging3.model.Result
import com.surajrathod.quotesappwithpaging3.network.QuoteApi
import com.surajrathod.quotesappwithpaging3.room.QuotesDatabase
import java.lang.Error

@OptIn(ExperimentalPagingApi::class)
class QuotesRemoteMediator(
    private val quoteApi: QuoteApi,
    private val quotesDatabase: QuotesDatabase
) : RemoteMediator<Int, Result>() {

    val quotesDao = quotesDatabase.quoteDao()
    val remoteKeysDao = quotesDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {

        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPostion(state)
                    remoteKeys?.next?.minus(1) ?: 1     //return current page
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prev ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.next ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }
            val response = quoteApi.getQuote(currentPage)

            val endOfPaginationReached = response.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            quotesDatabase.withTransaction {

                if(loadType==LoadType.REFRESH){
                    quotesDao.deleteQuote()
                    remoteKeysDao.deleteAllRemoteKeys()
                }

                quotesDao.addQuotes(response.results)
                val keys = response.results.map {
                    QuoteRemoteKeys(it._id, prevPage, nextPage)
                }
                remoteKeysDao.addAllRemoteKeys(keys)
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
        //fetch quotes from api
        //save this quotes + remote keys data in database
        //logic for states , REFRESH,PREPEND,APPEND

    }

    private suspend fun getRemoteKeyClosestToCurrentPostion(state: PagingState<Int, Result>): QuoteRemoteKeys? {
        return state.anchorPosition?.let {position ->
            state.closestItemToPosition(position)?._id?.let {id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Result>): QuoteRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            remoteKeysDao.getRemoteKeys(it._id)
        }
    }

    //retriving last record of last page, that is saved in state
    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Result>): QuoteRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { quote ->
            remoteKeysDao.getRemoteKeys(quote._id)
        }
    }
}