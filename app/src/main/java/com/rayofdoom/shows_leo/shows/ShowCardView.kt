package com.rayofdoom.shows_leo.shows

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.databinding.ViewShowItemBinding
import com.rayofdoom.shows_leo.utility_functions.displayShowImage

class ShowCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewShowItemBinding =
        ViewShowItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        val pixelPadding = context.resources.getDimensionPixelSize(R.dimen.card_padding)
        setPadding(pixelPadding, pixelPadding, pixelPadding, pixelPadding)
        clipToPadding = false
    }

    fun setTitle(title: String) {
        binding.showTitle.text = title
    }

    fun setDescription(desc: String?) {
        binding.showDescription.text = desc
    }

    fun setImage(url: String?) {
        if (url != null) {
            binding.showImage.displayShowImage(context, url)
        } else {
            binding.showImage.setImageResource(R.drawable.ic_profile_placeholder)
        }
    }
}