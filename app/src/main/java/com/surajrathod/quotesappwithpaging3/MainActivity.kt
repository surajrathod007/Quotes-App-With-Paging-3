package com.surajrathod.quotesappwithpaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.surajrathod.quotesappwithpaging3.adapter.QuotePagingAdapter
import com.surajrathod.quotesappwithpaging3.databinding.ActivityMainBinding
import com.surajrathod.quotesappwithpaging3.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var vm : QuotesViewModel
    lateinit var adapter : QuotePagingAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(QuotesViewModel::class.java)
        adapter = QuotePagingAdapter()
        binding.rvQuotes.adapter = adapter
        setupObserver()
    }

    private fun setupObserver() {
        vm.list.observe(this){
            adapter.submitData(lifecycle,it)
        }
    }
}