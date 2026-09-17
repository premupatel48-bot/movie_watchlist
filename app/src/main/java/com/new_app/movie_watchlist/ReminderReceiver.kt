package com.new_app.movie_watchlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val movieName = intent?.getStringExtra("movie_name") ?: "A movie"
        val watchTime = intent?.getStringExtra("watch_time") ?: ""

        val message = if (watchTime.isNotEmpty()) {
            "Your movie $movieName is ready to watch at $watchTime."
        } else {
            "$movieName is scheduled."
        }

        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
