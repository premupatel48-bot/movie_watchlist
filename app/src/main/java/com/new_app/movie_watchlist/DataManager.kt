package com.new_app.movie_watchlist

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class DataManager(private val context: Context) {

    private val PREF_NAME = "MovieWatchlistPrefs"
    private val KEY_MOVIES_PREFIX = "movies_"
    private val KEY_CURRENT_USER = "current_user"

    private fun getPrefs(): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveCurrentUser(username: String) {
        val editor = getPrefs().edit()
        editor.putString(KEY_CURRENT_USER, username)
        editor.apply()
    }

    fun getCurrentUser(): String? {
        return getPrefs().getString(KEY_CURRENT_USER, null)
    }

    fun logout() {
        val editor = getPrefs().edit()
        editor.remove(KEY_CURRENT_USER)
        editor.apply()
    }

    fun saveMovies(username: String, movieList: List<Movie>) {
        val jsonArray = JSONArray()
        for (movie in movieList) {
            jsonArray.put(movie.toJsonObject())
        }
        val editor = getPrefs().edit()
        editor.putString(KEY_MOVIES_PREFIX + username, jsonArray.toString())
        editor.apply()
    }

    fun loadMovies(username: String): MutableList<Movie> {
        val movies = mutableListOf<Movie>()
        val jsonString = getPrefs().getString(KEY_MOVIES_PREFIX + username, null)
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                movies.add(Movie.fromJsonObject(jsonObject))
            }
        }
        return movies
    }

    fun addMovie(username: String, movie: Movie) {
        val movies = loadMovies(username)
        movies.add(movie)
        saveMovies(username, movies)
    }

    fun updateMovie(username: String, updatedMovie: Movie) {
        val movies = loadMovies(username)
        for (i in 0 until movies.size) {
            if (movies[i].id == updatedMovie.id) {
                movies[i] = updatedMovie
                break
            }
        }
        saveMovies(username, movies)
    }

    fun deleteMovie(username: String, movieId: String) {
        val movies = loadMovies(username)
        val iterator = movies.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().id == movieId) {
                iterator.remove()
                break
            }
        }
        saveMovies(username, movies)
    }
}
