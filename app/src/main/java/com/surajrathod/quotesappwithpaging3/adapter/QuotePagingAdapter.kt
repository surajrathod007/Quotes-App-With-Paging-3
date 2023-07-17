package com.surajrathod.quotesappwithpaging3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.surajrathod.quotesappwithpaging3.databinding.ItemQuoteLayoutBinding
import com.surajrathod.quotesappwithpaging3.model.Result

class QuotePagingAdapter :
    PagingDataAdapter<Result, QuotePagingAdapter.QuotesViewHolder>(COMPARATOR) {

    class QuotesViewHolder(private val binding: ItemQuoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val txtQuote = binding.txtQuote
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val p = getItem(position)
        if (p != null) {
            holder.txtQuote.text = p.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        return QuotesViewHolder(
            ItemQuoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }
}