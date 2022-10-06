package edu.csueb.codepath.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/tv/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MovieFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_fragment_list, container, false)
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(recyclerView)
        return view
    }


private fun updateAdapter(recyclerView: RecyclerView) {
    val client = AsyncHttpClient()
    val params = RequestParams()
    params["api-key"] = API_KEY
    client["https://api.themoviedb.org/3/tv/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
            params,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                    //Toast.makeText(context, "enter on success", Toast.LENGTH_LONG).show()
                    // Access a JSON array response with `json.jsonArray`
                    Log.d("DEBUG ARRAY", json?.jsonArray.toString())
                    // Access a JSON object response with `json.jsonObject`
                    //Log.d("DEBUG OBJECT", json?.jsonObject.toString())
                    Log.d("DEBUG", json.toString())

                    //val resultsJson: JSONObject = json?.jsonObject?.get("results") as JSONObject
                    val resultsJson = json?.jsonObject?.get("results").toString()
                    //val movieRawJson: String = resultsJson.get("movies").toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type

                    val models: List<Movie> = gson.fromJson(resultsJson, arrayMovieType)
                    recyclerView.adapter = MovieRecyclerViewAdapter(models, this@MovieFragment)

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(context, "onFailure entered", Toast.LENGTH_LONG).show()
                    // Handle the failure and alert the user to retry
                    if (response != null) {
                        Log.e("ERROR", response)
                    }
                }


            }]
    //Toast.makeText(context, "After Client call", Toast.LENGTH_LONG).show()
}

    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }
}