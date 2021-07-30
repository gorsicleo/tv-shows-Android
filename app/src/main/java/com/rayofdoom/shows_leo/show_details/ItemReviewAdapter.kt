package com.rayofdoom.shows_leo.show_details

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rayofdoom.shows_leo.databinding.ItemReviewBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.utility_functions.displayAvatar
private const val OFFLINE_SUFFIX = " (Offline)"

class ItemReviewAdapter(
    private var reviews: List<Review>,
    private var context: Context
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



    inner class ItemReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(review: Review) {
            binding.apply {
                itemReviewUsername.text = review.user.email
                itemReviewUserReview.text = review.comment
                itemReviewRating.text = review.rating.toString()
                itemReviewUserProfilePicture.displayAvatar(context, review.user.imageUrl)
                //EXPERIMENTAL!!
                if (review.id<=0){
                    itemReviewUsername.text = review.user.email+ OFFLINE_SUFFIX
                    itemReviewUsername.setTextColor(Color.GRAY)
                }
            }
        }
    }

}