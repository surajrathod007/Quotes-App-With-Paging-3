package com.surajrathod.quotesappwithpaging3.di

import com.surajrathod.quotesappwithpaging3.network.QuoteApi
import com.surajrathod.quotesappwithpaging3.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideQuotesApi(retrofit: Retrofit): QuoteApi = retrofit.create(QuoteApi::class.java)

}