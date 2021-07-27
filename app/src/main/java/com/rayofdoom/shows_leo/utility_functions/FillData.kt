package com.rayofdoom.shows_leo.utility_functions

import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.User

fun fillReviewData(): MutableList<Review> {
    return mutableListOf(
        Review(2345,"test",5,5, User(23,"Leo@test.hr",null)))
}

fun fillShowsData(): List<Show> {
    val imageResource = "https://m.media-amazon.com/images/M/MV5BZjAzMDUzOWEtY2U3NS00ZjQ4LWJiNDAtMTI5YjBlNjg2M2RiXkEyXkFqcGdeQXVyNzQwNzQwMjA@._V1_.jpg"
    return listOf(
        Show(1, "Krim tim 2", "R.string.krim_tim_2_description", imageResource,3.0,4),
        Show(2, "Krv nije voda", "R.string.krv_nije_voda_description", imageResource,3.0,4),
        Show(3, "Sudnica", "R.string.sudnica_description", imageResource,3.0,4),
        Show(4, "KBC", "R.string.kbc_description", imageResource,3.0,4),
        Show(5, "Bibin svijet", "R.string.bibin_svijet_description", imageResource,3.0,4),
        Show(6, "Ne daj se nina", "R.string.ne_daj_se_nina_desription", imageResource,3.0,4),
        Show(
            7, "Zabranjena ljubav",
            "R.string.zabranjena_ljubav_description",
            imageResource,3.0,4
        )
    )
}