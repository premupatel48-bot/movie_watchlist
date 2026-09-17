package com.new_app.movie_watchlist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentUser: String? = null
    private var dataManager: DataManager? = null
    private var adapter: MovieAdapter? = null
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataManager = DataManager(this)
        currentUser = dataManager?.getCurrentUser()

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val tvWelcome = findViewById<TextView>(R.id.tvWelcomeUser)
        tvWelcome.text = "Welcome, $currentUser"

        val lvMovies = findViewById<ListView>(R.id.lvMovies)
        val fabAdd = findViewById<View>(R.id.fabAddMovie)
        val btnLogout = findViewById<View>(R.id.btnLogout)

        adapter = MovieAdapter()
        lvMovies.adapter = adapter

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddMovieActivity::class.java))
        }

        btnLogout.setOnClickListener {
            dataManager?.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        lvMovies.setOnItemClickListener { _, _, position, _ ->
            val movie = movies[position]
            val intent = Intent(this, AddMovieActivity::class.java)
            intent.putExtra("movie_id", movie.id)
            startActivity(intent)
        }

        lvMovies.setOnItemLongClickListener { _, _, position, _ ->
            val movie = movies[position]
            showDeleteDialog(movie)
            true
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        currentUser?.let {
            movies.clear()
            movies.addAll(dataManager?.loadMovies(it) ?: mutableListOf())
            adapter?.notifyDataSetChanged()
        }
    }

    private fun showDeleteDialog(movie: Movie) {
        AlertDialog.Builder(this)
            .setTitle("Delete Movie")
            .setMessage("Are you sure you want to delete ${movie.name}?")
            .setPositiveButton("Delete") { _, _ ->
                currentUser?.let {
                    dataManager?.deleteMovie(it, movie.id)
                    refreshList()
                    Toast.makeText(this, "Movie deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    inner class MovieAdapter : BaseAdapter() {
        override fun getCount(): Int = movies.size
        override fun getItem(position: Int): Any = movies[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.item_movie, parent, false)

            val movie = movies[position]

            val ivPoster = view.findViewById<ImageView>(R.id.ivPoster)
            val tvName = view.findViewById<TextView>(R.id.tvMovieName)
            val tvGenreYear = view.findViewById<TextView>(R.id.tvGenreYear)
            val tvStatus = view.findViewById<TextView>(R.id.tvStatus)
            val tvSchedule = view.findViewById<TextView>(R.id.tvSchedule)

            ivPoster.setImageResource(movie.posterResId)
            tvName.text = movie.name
            tvGenreYear.text = "${movie.genre} | ${movie.year}"
            tvStatus.text = if (movie.isWatched) "Watched (${movie.rating}/5)" else "Unwatched"
            tvStatus.setTextColor(if (movie.isWatched) 0xFF4CAF50.toInt() else 0xFFE50914.toInt())
            
            if (movie.watchDate.isNotEmpty() && movie.watchTime.isNotEmpty()) {
                tvSchedule.text = "Scheduled: ${movie.watchDate}, ${movie.watchTime}"
                tvSchedule.visibility = View.VISIBLE
            } else {
                tvSchedule.visibility = View.GONE
            }

            return view
        }
    }
}
