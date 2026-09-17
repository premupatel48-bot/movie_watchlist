package com.new_app.movie_watchlist

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddMovieActivity : AppCompatActivity() {

    private var selectedPosterResId: Int = R.drawable.img
    private var selectedDate: Calendar = Calendar.getInstance()
    private var isEditMode = false
    private var editMovieId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        val tvTitle = findViewById<TextView>(R.id.tvScreenTitle)
        val etName = findViewById<EditText>(R.id.etMovieName)
        val etGenre = findViewById<EditText>(R.id.etGenre)
        val etYear = findViewById<EditText>(R.id.etYear)
        val etDesc = findViewById<EditText>(R.id.etDescription)
        val btnDate = findViewById<Button>(R.id.btnDate)
        val btnTime = findViewById<Button>(R.id.btnTime)
        val cbWatched = findViewById<CheckBox>(R.id.cbWatched)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val poster1 = findViewById<ImageView>(R.id.ivPoster1)
        val poster2 = findViewById<ImageView>(R.id.ivPoster2)
        val poster3 = findViewById<ImageView>(R.id.ivPoster3)
        val poster4 = findViewById<ImageView>(R.id.ivPoster4)

        val dataManager = DataManager(this)
        val currentUser = dataManager.getCurrentUser()

        updatePosterSelectionUI()

        if (currentUser == null) {
            finish()
            return
        }

        // Edit mode check
        val intentData = intent
        if (intentData.hasExtra("movie_id")) {
            isEditMode = true
            editMovieId = intentData.getStringExtra("movie_id")
            tvTitle.text = "Edit Movie"
            
            val movies = dataManager.loadMovies(currentUser)
            val movie = movies.find { it.id == editMovieId }
            movie?.let {
                etName.setText(it.name)
                etGenre.setText(it.genre)
                etYear.setText(it.year.toString())
                etDesc.setText(it.description)
                cbWatched.isChecked = it.isWatched
                ratingBar.rating = it.rating
                selectedPosterResId = it.posterResId
                updatePosterSelectionUI()
                if (it.isWatched) ratingBar.visibility = View.VISIBLE
                
                btnDate.text = it.watchDate
                btnTime.text = it.watchTime
            }
        }

        cbWatched.setOnCheckedChangeListener { _, isChecked ->
            ratingBar.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        poster1.setOnClickListener { 
            selectedPosterResId = R.drawable.img
            updatePosterSelectionUI()
        }
        poster2.setOnClickListener { 
            selectedPosterResId = R.drawable.img_1
            updatePosterSelectionUI()
        }
        poster3.setOnClickListener { 
            selectedPosterResId = R.drawable.img_2
            updatePosterSelectionUI()
        }
        poster4.setOnClickListener { 
            selectedPosterResId = R.drawable.img_3
            updatePosterSelectionUI()
        }

        btnDate.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate.set(Calendar.YEAR, y)
                selectedDate.set(Calendar.MONTH, m)
                selectedDate.set(Calendar.DAY_OF_MONTH, d)
                btnDate.text = "$d/${m + 1}/$y"
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnTime.setOnClickListener {
            TimePickerDialog(this, { _, h, min ->
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, min)
                val ampm = if (h < 12) "AM" else "PM"
                val h12 = if (h % 12 == 0) 12 else h % 12
                btnTime.text = String.format("%02d:%02d %s", h12, min, ampm)
            }, selectedDate.get(Calendar.HOUR_OF_DAY), selectedDate.get(Calendar.MINUTE), false).show()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val genre = etGenre.text.toString().trim()
            val yearStr = etYear.text.toString().trim()
            val desc = etDesc.text.toString().trim()

            if (name.isEmpty() || genre.isEmpty() || yearStr.isEmpty() || 
                btnDate.text == "Select Date" || btnTime.text == "Select Time") {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val year = yearStr.toIntOrNull() ?: 0
            if (year < 1888 || year > 2100) {
                Toast.makeText(this, "Please enter a valid year", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dateStr = btnDate.text.toString()
            val timeStr = btnTime.text.toString()

            val movie = Movie(
                id = editMovieId ?: UUID.randomUUID().toString(),
                name = name,
                posterResId = selectedPosterResId,
                genre = genre,
                year = year,
                description = desc,
                isWatched = cbWatched.isChecked,
                watchDate = dateStr,
                watchTime = timeStr,
                rating = ratingBar.rating
            )

            if (isEditMode) {
                dataManager.updateMovie(currentUser, movie)
            } else {
                dataManager.addMovie(currentUser, movie)
            }

            scheduleReminder(movie)
            
            Toast.makeText(this, "Movie Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updatePosterSelectionUI() {
        val p1 = findViewById<ImageView>(R.id.ivPoster1)
        val p2 = findViewById<ImageView>(R.id.ivPoster2)
        val p3 = findViewById<ImageView>(R.id.ivPoster3)
        val p4 = findViewById<ImageView>(R.id.ivPoster4)

        p1.alpha = if (selectedPosterResId == R.drawable.img) 1.0f else 0.5f
        p2.alpha = if (selectedPosterResId == R.drawable.img_1) 1.0f else 0.5f
        p3.alpha = if (selectedPosterResId == R.drawable.img_2) 1.0f else 0.5f
        p4.alpha = if (selectedPosterResId == R.drawable.img_3) 1.0f else 0.5f
    }

    private fun scheduleReminder(movie: Movie) {
        val reminderTime = selectedDate.timeInMillis - (1 * 60 * 1000) // 30 minutes before
        
        if (reminderTime < System.currentTimeMillis()) return

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        intent.putExtra("movie_name", movie.name)
        intent.putExtra("watch_time", movie.watchTime)

        val pendingIntent = PendingIntent.getBroadcast(
            this, movie.id.hashCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent)
    }
}
