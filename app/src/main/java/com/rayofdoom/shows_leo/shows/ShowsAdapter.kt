package com.rayofdoom.shows_leo.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rayofdoom.shows_leo.databinding.ViewShowItemBinding
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.displayShowImage

class ShowsAdapter(
    private var items: List<Show>,
    private var context: Context,
    private val onClickCallback: (Show) -> Unit,

    ) : RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context))
        return ShowsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ShowsViewHolder(private val binding: ViewShowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Show) {
            binding.showTitle.text = item.showTitle
            binding.showDescription.text = item.showDescription
            item.imageResource?.let { binding.showImage.displayShowImage(context, it) }
            binding.root.setOnClickListener {
                onClickCallback(item)
            }
        }
    }


}