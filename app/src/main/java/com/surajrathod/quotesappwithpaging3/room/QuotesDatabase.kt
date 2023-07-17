package com.surajrathod.quotesappwithpaging3.room

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.surajrathod.quotesappwithpaging3.model.QuoteRemoteKeys
import com.surajrathod.quotesappwithpaging3.model.Result

@Database(entities = [Result::class,QuoteRemoteKeys::class], version = 1)
abstract class QuotesDatabase : RoomDatabase() {

    abstract fun quoteDao() : QuotesDao
    abstract fun remoteKeysDao() : RemoteKeysDao

}