package com.rayofdoom.shows_leo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayofdoom.shows_leo.databinding.ActivityShowsBinding
import com.rayofdoom.shows_leo.model.Show

class ShowsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowsBinding

    private val shows = listOf(
        Show("Krim tim 2", R.string.krim_tim_2_description, R.drawable.kt2),
        Show("Krv nije voda", R.string.krv_nije_voda_description, R.drawable.krv_nije_voda_1),
        Show("Sudnica", R.string.sudnica_description, R.drawable.sudnica),
        Show("KBC", R.string.kbc_description, R.drawable.kbc),
        Show("Bibin svijet", R.string.bibin_svijet_description, R.drawable.bibin_svijet),
        Show("Ne daj se nina", R.string.ne_daj_se_nina_desription, R.drawable.ne_daj_se_nina),
        Show(
            "Zabranjena ljubav",
            R.string.zabranjena_ljubav_description,
            R.drawable.zabranjena_ljubav
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityShowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = ShowsAdapter(shows) { name ->
            intent = ShowDetailsActivity.buildIntent(this, name)
            startActivity(intent)
        }
    }
}