package edu.csueb.codepath.flixster

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("poster_path")
    var poster: String? = null

    @SerializedName("name")
    var title: String? = null

    @SerializedName("overview")
    var description: String? = null

    @SerializedName("backdrop_path")
    var backdropPoster: String? = null
}