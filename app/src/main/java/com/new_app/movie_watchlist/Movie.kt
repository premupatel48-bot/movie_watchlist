package com.new_app.movie_watchlist

import org.json.JSONObject

data class Movie(
    var id: String,
    var name: String,
    var posterResId: Int,
    var genre: String,
    var year: Int,
    var description: String,
    var isWatched: Boolean,
    var watchDate: String,
    var watchTime: String,
    var rating: Float
) {
    fun toJsonObject(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("name", name)
        json.put("posterResId", posterResId)
        json.put("genre", genre)
        json.put("year", year)
        json.put("description", description)
        json.put("isWatched", isWatched)
        json.put("watchDate", watchDate)
        json.put("watchTime", watchTime)
        json.put("rating", rating.toDouble())
        return json
    }

    companion object {
        fun fromJsonObject(json: JSONObject): Movie {
            return Movie(
                json.getString("id"),
                json.getString("name"),
                json.getInt("posterResId"),
                json.getString("genre"),
                json.getInt("year"),
                json.getString("description"),
                json.getBoolean("isWatched"),
                json.getString("watchDate"),
                json.getString("watchTime"),
                json.getDouble("rating").toFloat()
            )
        }
    }
}
