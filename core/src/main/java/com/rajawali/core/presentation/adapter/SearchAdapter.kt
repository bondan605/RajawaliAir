package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.databinding.ItemSearchResultBinding
import com.rajawali.core.domain.model.SearchModel

class SearchAdapter :
    ListAdapter<SearchModel, SearchAdapter.SearchViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnAirportClickCallback: OnAirportClickCallback

    fun setOnAirportClickCallback(onAirportClickCallback: OnAirportClickCallback) {
        this.setOnAirportClickCallback = onAirportClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val result = getItem(position)

        ItemSearchResultBinding.bind(holder.itemView).apply {
            this.clAirport.setOnClickListener {
                setOnAirportClickCallback.onAirportClickCallback(result)
            }
        }

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

    interface OnAirportClickCallback {
        fun onAirportClickCallback(airport: SearchModel)
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