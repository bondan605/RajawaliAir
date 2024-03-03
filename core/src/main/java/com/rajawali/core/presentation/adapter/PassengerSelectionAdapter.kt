package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.rajawali.core.R
import com.rajawali.core.databinding.ItemPassengerCardBinding
import com.rajawali.core.domain.model.PassengerInputModel

class PassengerSelectionAdapter :
    ListAdapter<PassengerInputModel, PassengerSelectionAdapter.PassengerViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnPassengerClickCallback: OnPassengerClickCallback

    fun setOnPassengerClickCallback(onPassengerClickCallback: OnPassengerClickCallback) {
        this.setOnPassengerClickCallback = onPassengerClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val binding =
            ItemPassengerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {

        val passenger = getItem(position)

        if (passenger != null) {
            holder.bind(passenger)
        }
    }

    inner class PassengerViewHolder(private val binding: ItemPassengerCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(passenger: PassengerInputModel) {

            binding.cardPassenger.setOnClickListener {
                setOnPassengerClickCallback.onPassengerClickCallback(passenger)
            }

            setOnPassengerClickCallback.setOrderText(passenger, binding.tvOrder)

            setOnPassengerClickCallback.setBackgroundColor(
                passenger,
                binding.cardPassenger,
                binding.tvOrder,
                binding.tvPassenger2
            )

            binding.tvPassenger2.text = itemView.context.getString(
                com.rajawali.common_resource.R.string.tv_passenger_detail,
                passenger.id,
                passenger.age
            )

        }
    }

    interface OnPassengerClickCallback {
        fun onPassengerClickCallback(passenger: PassengerInputModel)

        fun setBackgroundColor(
            passenger: PassengerInputModel,
            cardPassenger: MaterialCardView,
            tvOrder: TextView,
            tvPassenger: TextView
        )

        fun setOrderText(passenger: PassengerInputModel, tvOrder: TextView)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PassengerInputModel>() {
            override fun areItemsTheSame(
                oldItem: PassengerInputModel,
                newItem: PassengerInputModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PassengerInputModel,
                newItem: PassengerInputModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}