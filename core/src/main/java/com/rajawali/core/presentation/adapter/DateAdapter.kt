package com.rajawali.core.presentation.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.R
import com.rajawali.core.databinding.ItemPickDateBinding
import com.rajawali.core.util.DateFormat
import java.time.LocalDate

class DateAdapter :
    ListAdapter<LocalDate, DateAdapter.DateViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnDateClickCallback: OnDateClickCallback

    fun setOnTicketClickCallback(onDateClickCallback: OnDateClickCallback) {
        this.setOnDateClickCallback = onDateClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding =
            ItemPickDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {

        val date = getItem(position)

        ItemPickDateBinding.bind(holder.itemView).apply {
            this.tvDate.setOnClickListener {
                setOnDateClickCallback.onDateClickCallback(date)
            }
        }


        if (date != null) {
            holder.bind(date)
        }
    }

    inner class DateViewHolder(private val binding: ItemPickDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(localDate: LocalDate) {
            val date = DateFormat.getDayAndDateOnly(localDate)

            binding.tvDate.text = date

            //preferred date.
            //since the preferred date is always on position 5, which is the center.
            preferredDate()
        }

        private fun preferredDate() {
            val centerPosition = 5
            val currentPosition = adapterPosition

            if (currentPosition == centerPosition) {
                binding.tvDate.apply {
                    setTypeface(typeface, Typeface.BOLD)
                    setTextColor(itemView.context.getColor(R.color.main_color))
                }
                binding.tvBackground.visibility = View.VISIBLE
            }
        }
    }

    interface OnDateClickCallback {
        fun onDateClickCallback(date: LocalDate)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalDate>() {
            override fun areItemsTheSame(
                oldItem: LocalDate,
                newItem: LocalDate
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: LocalDate,
                newItem: LocalDate
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}