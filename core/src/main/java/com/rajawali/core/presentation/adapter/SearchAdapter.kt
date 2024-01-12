package com.rajawali.core.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.databinding.ItemSearchResultBinding
import com.rajawali.core.domain.model.SearchModel

class SearchAdapter :
    ListAdapter<SearchModel, SearchAdapter.SearchViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val result = getItem(position)

        Log.d("SearchAdapter", result.toString())
        if (result != null) {
            holder.bind(result)
        }
    }

    class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: SearchModel) {
            binding.tvCity.text = result.city
            binding.tvAirport.text = result.airport
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchModel>() {
            override fun areItemsTheSame(
                oldItem: SearchModel,
                newItem: SearchModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SearchModel,
                newItem: SearchModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}