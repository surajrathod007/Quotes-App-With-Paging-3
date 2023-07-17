package com.surajrathod.quotesappwithpaging3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_remote_keys")
data class QuoteRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val prev : Int?,
    val next : Int?
)
