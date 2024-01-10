package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.databinding.ItemCardPromotionBinding
import com.rajawali.core.domain.model.PromotionModel

class PromotionAdapter :
    ListAdapter<PromotionModel, PromotionAdapter.PromotionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val binding =
            ItemCardPromotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PromotionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
//        val actualPosition = position % currentList.size
//        val promotion = currentList[actualPosition]

        val promotion = getItem(position)

        if (promotion != null) {
            holder.bind(promotion)
        }
    }

    class PromotionViewHolder(private val binding: ItemCardPromotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(promotion: PromotionModel) {
            binding.tvDestinationLabel.text = promotion.title
            binding.tvDestinationRoute.text = promotion.route
            binding.tvDestinationPriceValue.text = promotion.price
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PromotionModel>() {
            override fun areItemsTheSame(
                oldItem: PromotionModel,
                newItem: PromotionModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PromotionModel,
                newItem: PromotionModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}