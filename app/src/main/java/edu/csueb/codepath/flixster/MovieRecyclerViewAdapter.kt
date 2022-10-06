package edu.csueb.codepath.flixster

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.annotations.SerializedName

const val MOVIE_EXTRA = "MOVIE_EXTRA"

class MovieRecyclerViewAdapter( private val movies: List<Movie>,
                                private val mListener: OnListFragmentInteractionListener?
                                )
    : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_fragment_item, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView),
    View.OnClickListener{
        var mItem: Movie? = null
        val mTitle: TextView = mView.findViewById<View>(R.id.tvTitle) as TextView
        val mDescription: TextView = mView.findViewById<View>(R.id.tvDescription) as TextView
        val mImagePoster: ImageView = mView.findViewById<View>(R.id.ivPosterPic) as ImageView
        //val mLinearLayout: ConstraintLayout = mView.findViewById<ConstraintLayout>(R.id.linearLayout)

        override fun toString(): String {
            return mTitle.toString() + " '" + mDescription.text + "'"
        }

        init {
            mView.setOnClickListener(this)
        }
        @SuppressLint("RestrictedApi")
        override fun onClick(v: View?) {
            //    val test = mView.absoluteAdapterPosition
            val moviex = movies[adapterPosition]

            val intent = Intent(v?.context, DetailActivity::class.java)
            intent.putExtra("Title", moviex.title)
            intent.putExtra("Overview", moviex.description)
            intent.putExtra("Poster", moviex.backdropPoster)
            val cont = getActivity(mView.context)
            cont?.startActivity(intent)
            //v?.context?.startActivity(intent)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        //val test = movies.get(holder.getAbsolute)

        val movie = movies[position]

        holder.mItem = movie
        holder.mTitle.text = movie.title
        holder.mDescription.text = movie.description
        val urlPoster = String.format("https://image.tmdb.org/t/p/w500/%s", movie.poster)
        val urlBackdrop = String.format("https://image.tmdb.org/t/p/w500/%s", movie.backdropPoster)
        //Log.d("DEBUG", "url link iv: $url")
        val image: String
        val orientation = holder.itemView.resources.configuration.orientation


        val radius = 30
        val margin = 10
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(holder.mView)
                .load(urlPoster)
                .placeholder(R.drawable.placeholder)
                .override(400, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                .centerInside()
                .transform(RoundedCorners(radius))
                .into(holder.mImagePoster)

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.d("tag", urlBackdrop)
            Glide.with(holder.mView)
                .load(urlBackdrop)
                .centerInside()
                .into(holder.mImagePoster)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}