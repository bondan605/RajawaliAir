package com.rajawali.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rajawali.core.R
import com.rajawali.core.databinding.ItemMealsBinding
import com.rajawali.core.domain.model.MealModel
import com.rajawali.core.presentation.viewModel.MealsViewModel

class MealsAdapter(private val mealsViewModel: MealsViewModel) :
    ListAdapter<MealModel, MealsAdapter.MealsViewHolder>(DIFF_CALLBACK) {

    private lateinit var setOnMealsClickCallback: OnMealsClickCallback

    fun setOnMealsClickCallback(onMealsClickCallback: OnMealsClickCallback) {
        this.setOnMealsClickCallback = onMealsClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val binding =
            ItemMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {

        val date = getItem(position)

        if (date != null) {
            holder.bind(date)
        }
    }

    inner class MealsViewHolder(private val binding: ItemMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meals: MealModel) {
//            binding.clMeal.setOnClickListener {}

            binding.cbMeal.setOnClickListener {
                var currentUser = 1
                mealsViewModel.currentPassenger.observeForever { id ->
                    currentUser = id
                }

                setOnMealsClickCallback.onMealsClickCallback(currentUser, meals)
            }

            setOnMealsClickCallback.setCheckBoxStatus(binding.cbMeal, meals)

            binding.tvMealName.text = meals.name
            binding.tvMealDescription.text = meals.description
            binding.tvMealPrice.text =
                itemView.context.getString(com.rajawali.common_resource.R.string.tv_price_ticket, meals.price)

            Glide
                .with(itemView.context)
                .load(meals.thumbnailUrl)
                .centerCrop()
                .into(binding.imgMeal)
        }
    }

    interface OnMealsClickCallback {
        fun onMealsClickCallback(currentUser: Int, meals: MealModel)

        fun setCheckBoxStatus(checkBox: CheckBox, meals: MealModel)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MealModel>() {
            override fun areItemsTheSame(
                oldItem: MealModel,
                newItem: MealModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MealModel,
                newItem: MealModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}