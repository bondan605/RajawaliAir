package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.R
import com.rajawali.core.databinding.ItemTicketBinding
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.util.DateFormat

class TicketAdapter :
    ListAdapter<FlightModel, TicketAdapter.FlightViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnTicketClickCallback: OnTicketClickCallback

    fun setOnTicketClickCallback(onTicketClickCallback: OnTicketClickCallback) {
        this.setOnTicketClickCallback = onTicketClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding =
            ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {

        val ticket = getItem(position)

        ItemTicketBinding.bind(holder.itemView).apply {
            this.itemTicket.setOnClickListener {
                setOnTicketClickCallback.onTicketClickCallback(ticket)
            }

            this.llRescheduleOptions.setOnClickListener {
                setOnTicketClickCallback.onRescheduleOptionsClickCallback(ticket)
            }
        }


        if (ticket != null) {
            holder.bind(ticket)
        }
    }

    inner class FlightViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ticket: FlightModel) {
            val flightLength = DateFormat.calculateTimeDifference(ticket.departureTime, ticket.arrivalTime)

            binding.priceTicket.text = itemView.context.getString(R.string.tv_price_ticket, ticket.seatPrice)
            binding.airplaneId.text = ticket.airplane.airplaneCode
            binding.point.text = itemView.context.getString(R.string.tv_point, ticket.points)
            binding.tvDepartureCityCodeLabel.text = ticket.sourceAirport.cityCode
            binding.tvArriveCityCodeLabel.text = ticket.destinationAirport.cityCode
            binding.tvDepartureTime.text = ticket.departureTime
            binding.tvArriveTime.text = ticket.arrivalTime
            binding.directTime.text = flightLength

            if (ticket.availableSeats < 0)
                binding.clParent.alpha = 0.5F
        }
    }

    interface OnTicketClickCallback {
        fun onTicketClickCallback(ticket: FlightModel)

        fun onRescheduleOptionsClickCallback(ticket: FlightModel)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FlightModel>() {
            override fun areItemsTheSame(
                oldItem: FlightModel,
                newItem: FlightModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FlightModel,
                newItem: FlightModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}