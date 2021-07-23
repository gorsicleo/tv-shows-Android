package com.rayofdoom.shows_leo.utility_functions

import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show

fun fillReviewData(): MutableList<Review> {
    return mutableListOf(
        Review("imenko.prezimenovic", "super je", R.drawable.ic_profile_placeholder, 5),
        Review("ffloreani", "bezveze", R.drawable.ic_profile_placeholder, 2),
        Review(
            "Leo",
            "Zapevati pred punom Kombank arenom je životna želja svakog pevača. Ja sam, zahvaljujući vama, svoju životnu želju ispunio 08. marta 2016. godine. Sve moje najlepše pesme posvećene su  damama, pa je nekako logičan korak bio da im posvetim i ceo jedan koncert.",
            R.drawable.ic_profile_placeholder,
            5
        )
    )
}

fun fillShowsData(): List<Show> {
    return listOf(
        Show(1, "Krim tim 2", R.string.krim_tim_2_description, R.drawable.kt2),
        Show(2, "Krv nije voda", R.string.krv_nije_voda_description, R.drawable.krv_nije_voda_1),
        Show(3, "Sudnica", R.string.sudnica_description, R.drawable.sudnica),
        Show(4, "KBC", R.string.kbc_description, R.drawable.kbc),
        Show(5, "Bibin svijet", R.string.bibin_svijet_description, R.drawable.bibin_svijet),
        Show(6, "Ne daj se nina", R.string.ne_daj_se_nina_desription, R.drawable.ne_daj_se_nina),
        Show(
            7, "Zabranjena ljubav",
            R.string.zabranjena_ljubav_description,
            R.drawable.zabranjena_ljubav
        )
    )
}

fun getShowById(id: Int): Show {
    return fillShowsData()[id - 1]
}