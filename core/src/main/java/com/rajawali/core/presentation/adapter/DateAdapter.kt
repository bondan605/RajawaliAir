package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.databinding.ItemPickDateBinding
import com.rajawali.core.util.DateMapper
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

    class DateViewHolder(private val binding: ItemPickDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(localDate: LocalDate) {
            val date = DateMapper.getDayAndDateOnly(localDate)

            binding.tvDate.text = date
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