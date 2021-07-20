package com.rayofdoom.shows_leo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rayofdoom.shows_leo.databinding.ItemReviewBinding
import com.rayofdoom.shows_leo.model.Review

class ItemReviewAdapter(
    private var reviews: List<Review>,
) : RecyclerView.Adapter<ItemReviewAdapter.ItemReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ItemReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun addItem(newReviewList: List<Review>) {
        reviews = newReviewList
        notifyItemInserted(reviews.lastIndex)
    }


    inner class ItemReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                itemReviewUsername.text = review.userName
                itemReviewUserReview.text = review.userReview
                itemReviewRating.text = review.userRating.toString()
                itemReviewUserProfilePicture.setImageResource(review.userProfilePicture)
            }

        }
    }


}