package com.rayofdoom.shows_leo.shows

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rayofdoom.shows_leo.model.Show


class ShowsAdapter(
    private var items: List<Show>,
    private var context: Context,
    private val onClickCallback: (Show) -> Unit,

    ) : RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val showCardView = ShowCardView(context)
        return ShowsViewHolder(showCardView)
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ShowsViewHolder(private val showCardView: ShowCardView) :
        RecyclerView.ViewHolder(showCardView) {
        fun bind(item: Show) {
            showCardView.setTitle(item.showTitle)
            showCardView.setDescription(item.showDescription)
            showCardView.setImage(item.imageResource)

            showCardView.setOnClickListener {
                onClickCallback(item)
            }
        }
    }


}