package com.zerodev.covidstats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zerodev.covidstats.databinding.ItemOnboardBinding
import com.zerodev.covidstats.model.Onboard

class OnboardAdapter(private val onboardListItem: List<Onboard>) :
    RecyclerView.Adapter<OnboardAdapter.OnBoardViewHolder>() {

    class OnBoardViewHolder(private val binding: ItemOnboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboard: Onboard) {
            binding.apply {
                Glide.with(itemView.context).load(onboard.image).into(ivActivity)
                tvTitle.text = onboard.title
                tvDescription.text = onboard.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {
        return OnBoardViewHolder(
            ItemOnboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = onboardListItem.size

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        holder.bind(onboardListItem[position])
    }
}