package com.surajrathod.quotesappwithpaging3.di

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import com.surajrathod.quotesappwithpaging3.room.QuotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuotesDatabase {
        return Room.databaseBuilder(context, QuotesDatabase::class.java, "quotes_db").build()
    }

}