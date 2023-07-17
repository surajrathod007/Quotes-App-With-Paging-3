package com.surajrathod.quotesappwithpaging3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surajrathod.quotesappwithpaging3.model.QuoteRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("select * from quotes_remote_keys where id =:id")
    suspend fun getRemoteKeys(id : String) : QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys : List<QuoteRemoteKeys>)

    @Query("delete from quotes_remote_keys")
    suspend fun deleteAllRemoteKeys()
}