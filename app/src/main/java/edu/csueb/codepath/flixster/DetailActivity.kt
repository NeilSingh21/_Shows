package edu.csueb.codepath.flixster

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class DetailActivity : AppCompatActivity() {
    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var ivDetailPoster: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvDetailTitle = findViewById(R.id.tvDetailTitle)
        tvDetailDescription = findViewById(R.id.tvDetailDescription)
        ivDetailPoster = findViewById(R.id.ivDetailPoster)

        val detailTitle = intent.getStringExtra("Title")
        val detailDescription = intent.getStringExtra("Overview")
        val detailPoster = intent.getStringExtra("Poster")

        tvDetailTitle.text = detailTitle
        tvDetailDescription.text = detailDescription

        val urlBackdrop = String.format("https://image.tmdb.org/t/p/w500/%s", detailPoster)

        val radius = 30

        Glide.with(this)
            .load(urlBackdrop)
            .placeholder(R.drawable.placeholder)
            .centerInside()
            .transform(RoundedCorners(radius))
            .into(ivDetailPoster)
    }
}
