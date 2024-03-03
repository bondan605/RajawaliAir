package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.rajawali.core.R
import com.rajawali.core.databinding.ItemBaggagesBinding
import com.rajawali.core.domain.enums.BaggageEnum
import com.rajawali.core.domain.model.PassengerInputModel
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel

class BaggageAdapter(private val viewModel: TravelAddsOnViewModel) :
    ListAdapter<PassengerInputModel, BaggageAdapter.BaggageViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnBaggageClickCallback: OnBaggageClickCallback

    fun setOnBaggageClickCallback(onBaggageClickCallback: OnBaggageClickCallback) {
        this.setOnBaggageClickCallback = onBaggageClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaggageViewHolder {
        val binding =
            ItemBaggagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaggageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaggageViewHolder, position: Int) {

        val passenger = getItem(position)

        if (passenger != null) {
            holder.bind(passenger)
        }
    }

    inner class BaggageViewHolder(private val binding: ItemBaggagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(passengers: PassengerInputModel) {

            binding.tvPassenger.text = itemView.context.getString(
                com.rajawali.common_resource.R.string.tv_passenger_detail,
                passengers.id,
                passengers.age.name
            )

            binding.btn0Kg.setOnClickListener {
                setOnBaggageClickCallback.onBaggageClickCallback(passengers.id, BaggageEnum.KG0)
            }
            binding.btn5Kg.setOnClickListener {
                setOnBaggageClickCallback.onBaggageClickCallback(passengers.id, BaggageEnum.KG5)
            }
            binding.btn10Kg.setOnClickListener {
                setOnBaggageClickCallback.onBaggageClickCallback(passengers.id, BaggageEnum.KG10)
            }
            binding.btn20Kg.setOnClickListener {
                setOnBaggageClickCallback.onBaggageClickCallback(passengers.id, BaggageEnum.KG20)
            }
            binding.btn30Kg.setOnClickListener {
                setOnBaggageClickCallback.onBaggageClickCallback(passengers.id, BaggageEnum.KG30)
            }
            binding.btn40Kg.setOnClickListener {
                setOnBaggageClickCallback.onBaggageClickCallback(passengers.id, BaggageEnum.KG40)
            }

            viewModel.passengerBaggageList.observeForever { list ->
                list.map { passenger ->
                    val baggageViews = mapOf(
                        BaggageEnum.KG0 to Triple(binding.btn0Kg, binding.tv0kg, binding.tvIdr0),
                        BaggageEnum.KG5 to Triple(
                            binding.btn5Kg,
                            binding.tv5kg,
                            binding.tvIdr250000
                        ),
                        BaggageEnum.KG10 to Triple(
                            binding.btn10Kg,
                            binding.tv10kg,
                            binding.tvIdr500000
                        ),
                        BaggageEnum.KG20 to Triple(
                            binding.btn20Kg,
                            binding.tv20kg,
                            binding.tvIdr1000000
                        ),
                        BaggageEnum.KG30 to Triple(
                            binding.btn30Kg,
                            binding.tv30kg,
                            binding.tvIdr2000000
                        ),
                        BaggageEnum.KG40 to Triple(
                            binding.btn40Kg,
                            binding.tv40kg,
                            binding.tv40KgIdr2000000
                        )
                    )

                    if (passenger.id == passengers.id)
                        baggageViews.forEach { (baggage, views) ->
                            val isSelected = passenger.baggage == baggage
                            changeBackground(isSelected, views.first, views.second, views.third)
                        }
                }
            }

        }

        private fun changeBackground(
            isSelected: Boolean,
            cardView: MaterialCardView,
            weight: TextView,
            price: TextView
        ) {
            val on = itemView.resources.getColor(com.rajawali.common_resource.R.color.dodger_blue)
            val onBackground = itemView.resources.getColor(com.rajawali.common_resource.R.color.lily_white)
            val off = itemView.resources.getColor(com.rajawali.common_resource.R.color.alto)
            val offBackground = itemView.resources.getColor(com.rajawali.common_resource.R.color.white)
            val offBlack = itemView.resources.getColor(com.rajawali.common_resource.R.color.black)

            cardView.setCardBackgroundColor(if (isSelected) onBackground else offBackground)
            cardView.strokeColor = if (isSelected) on else off
            weight.setTextColor(if (isSelected) on else offBlack)
            price.setTextColor(if (isSelected) on else offBlack)
        }
    }

    interface OnBaggageClickCallback {
        fun onBaggageClickCallback(id: Int, baggage: BaggageEnum)
//        fun on5kgClickCallback(id: Int, baggage: BaggageEnum)
//        fun on10kgClickCallback(id: Int, baggage: BaggageEnum)
//        fun on20kgClickCallback(id: Int, baggage: BaggageEnum)
//        fun on30kgClickCallback(id: Int, baggage: BaggageEnum)
//        fun on40kgClickCallback(id: Int, baggage: BaggageEnum)
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