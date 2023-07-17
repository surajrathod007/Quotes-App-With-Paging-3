package com.surajrathod.quotesappwithpaging3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.surajrathod.quotesappwithpaging3.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val repository: QuotesRepository) : ViewModel() {

    val list = repository.getQuotes().cachedIn(viewModelScope)
}