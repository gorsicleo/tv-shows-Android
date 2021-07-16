package com.rayofdoom.shows_leo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rayofdoom.shows_leo.databinding.ItemReviewBinding
import com.rayofdoom.shows_leo.model.Review

class ItemReviewAdapter (
    private var items: List<Review>,
): RecyclerView.Adapter<ItemReviewAdapter.ItemReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ItemReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemReviewViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ItemReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Review){
            binding.itemReviewUsername.text = item.userName
            binding.itemReviewUserReview.text = item.userReview
            binding.itemReviewRating.text = item.userRating.toString()
            binding.itemReviewUserProfilePicture.setImageResource(item.userProfilePicture)
        }
    }


}