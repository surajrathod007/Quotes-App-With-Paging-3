package com.surajrathod.quotesappwithpaging3.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surajrathod.quotesappwithpaging3.model.Result

@Dao
interface QuotesDao {

    @Query("select * from quotes")
    fun getQuote() : PagingSource<Int,Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(quotes : List<Result>)

    @Query("delete from quotes")
    suspend fun deleteQuote()
}