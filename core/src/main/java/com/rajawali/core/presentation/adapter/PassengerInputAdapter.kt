package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.databinding.ItemPassengerInputBinding
import com.rajawali.core.domain.model.PassengerInputModel
import timber.log.Timber
import java.util.Locale

class PassengerInputAdapter :
    ListAdapter<PassengerInputModel, PassengerInputAdapter.PassengerInputViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnInputClickCallback: OnInputClickCallback

    fun setOnInputClickCallback(onInputClickCallback: OnInputClickCallback) {
        this.setOnInputClickCallback = onInputClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerInputViewHolder {
        val binding =
            ItemPassengerInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerInputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PassengerInputViewHolder, position: Int) {

        val input = getItem(position)

        ItemPassengerInputBinding.bind(holder.itemView).apply {
            this.cardPassenger.setOnClickListener {
                setOnInputClickCallback.onInputClickCallback(input)
            }
        }


        if (input != null) {
            holder.bind(input)
        }
    }

    //TODO adapter look like done.
    class PassengerInputViewHolder(private val binding: ItemPassengerInputBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(passenger: PassengerInputModel) {
            val passengerAge = passenger.age.name.lowercase().capitalize()
            val age =
                passenger.age.name.replaceFirstChar {
                    if (it.isLowerCase())
                        it.titlecase(Locale.getDefault())
                    else
                        it.toString()
                }

            binding.tvPassengerDetailOther.text =
                "Passenger ${passenger.id} ( $passengerAge )"

//            binding.tvPassengerDetailOther.text = passenger.id.toString() + " " + passenger.age.name

            Timber.d("${passenger.id} & $age")
        }
    }

    interface OnInputClickCallback {
        fun onInputClickCallback(input: PassengerInputModel)
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