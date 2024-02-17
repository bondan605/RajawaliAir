package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajawali.core.R
import com.rajawali.core.databinding.ItemNotificationBinding
import com.rajawali.core.domain.model.NotificationItemModel
import com.rajawali.core.util.DateFormat

class NotificationAdapter :
    ListAdapter<NotificationItemModel, NotificationAdapter.NotificationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val data = getItem(position)

        if (data != null) {
            holder.bind(data)
        }
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NotificationItemModel) {
            val date = DateFormat.formatDateStringToAbbreviatedString(data.createdAt)
            val time = data.createdAt.slice(11..15)

            binding.tvDate.text =
                itemView.context.getString(R.string.tv_date_and_time_value, time, date)
            binding.tvDescription.text = data.description
        }

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotificationItemModel>() {
            override fun areItemsTheSame(
                oldItem: NotificationItemModel,
                newItem: NotificationItemModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NotificationItemModel,
                newItem: NotificationItemModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}