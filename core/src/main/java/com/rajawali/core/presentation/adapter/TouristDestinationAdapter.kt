package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.databinding.ItemTouristDestinationBinding
import com.rajawali.core.domain.model.TouristDestinationModel

class TouristDestinationAdapter :
    ListAdapter<TouristDestinationModel, TouristDestinationAdapter.TouristDestinationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristDestinationViewHolder {
        val binding =
            ItemTouristDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TouristDestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TouristDestinationViewHolder, position: Int) {

        val destination = getItem(position)

        if (destination != null) {
            holder.bind(destination)
        }
    }

    class TouristDestinationViewHolder(private val binding: ItemTouristDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(promotion: TouristDestinationModel) {
            binding.tvDestinationLabel.text = promotion.title
            binding.tvDestinationRoute.text = promotion.route
            binding.tvDestinationPrice.text = promotion.price
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TouristDestinationModel>() {
            override fun areItemsTheSame(
                oldItem: TouristDestinationModel,
                newItem: TouristDestinationModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TouristDestinationModel,
                newItem: TouristDestinationModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}