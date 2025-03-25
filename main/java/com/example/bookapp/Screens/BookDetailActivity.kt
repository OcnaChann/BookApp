package com.example.bookapp.Screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bookapp.FavoriteBook
import com.example.bookapp.viewmodel.FavoriteBookViewmodel
import com.example.bookapp.R
import com.example.bookapp.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailActivity : AppCompatActivity() {

    private lateinit var bookCoverImage: ImageView
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookPublishedDate: TextView
    private lateinit var bookDescription: TextView
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var favoriteButton: ImageView
    private lateinit var favoriteBookViewModel: FavoriteBookViewmodel

    private var currentBook: FavoriteBook? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Initialize views
        bookCoverImage = findViewById(R.id.bookCoverImage)
        bookTitle = findViewById(R.id.bookTitle)
        bookAuthor = findViewById(R.id.bookAuthor)
        bookPublishedDate = findViewById(R.id.bookPublishedDate)
        bookDescription = findViewById(R.id.bookDescription)
        loadingSpinner = findViewById(R.id.loadingSpinner)
        favoriteButton = findViewById(R.id.favoriteButton)

        // Initialize ViewModel
        favoriteBookViewModel = ViewModelProvider(this).get(FavoriteBookViewmodel::class.java)

        // Fetch book details from intent
        val bookTitleString = intent.getStringExtra("BOOK_TITLE")
        if (bookTitleString != null) {
            fetchBookDetails(bookTitleString)
        }

        // Handle fav Button Click
        favoriteButton.setOnClickListener {
            currentBook?.let { book ->
                if (isFavorite) {
                    removeFromFavorites(book)
                } else {
                    addBookToFavorites(book)
                }
            }
        }
    }

    private fun fetchBookDetails(bookTitleString: String) {
        val apiKey = "AIzaSyDg6H8k3IW4N4zwN6Rh87iJDJYFP_yRNRI"

        lifecycleScope.launch {
            try {
                loadingSpinner.visibility = View.VISIBLE

                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getBooks("intitle:$bookTitleString", apiKey)
                }

                response.items?.firstOrNull()?.let { book ->
                    runOnUiThread {
                        // Set book details
                        bookTitle.text = book.volumeInfo.title
                        bookAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author"
                        bookPublishedDate.text = book.volumeInfo.publishedDate
                        bookDescription.text = book.volumeInfo.description ?: "No description available"

                        // Load book cover
                        book.volumeInfo.imageLinks?.thumbnail?.let { imageUrl ->
                            Glide.with(this@BookDetailActivity)
                                .load(imageUrl)
                                .placeholder(R.drawable.ic_placeholder)
                                .error(R.drawable.ic_error)
                                .into(bookCoverImage)
                        }

                        // Create fav book object
                        currentBook = FavoriteBook(
                            id = book.id,
                            title = book.volumeInfo.title,
                            author = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author",
                            publishedDate = book.volumeInfo.publishedDate ?: "Unknown date",
                            description = book.volumeInfo.description ?: "No description available",
                            thumbnail = book.volumeInfo.imageLinks?.thumbnail
                        )

                        // Check if book is already fav
                        checkIfFavorite(book.id)
                        loadingSpinner.visibility = View.GONE
                    }
                } ?: run {
                    showError("No book details found.")
                }
            } catch (e: Exception) {
                showError("Error fetching book details.")
            }
        }
    }

    private fun checkIfFavorite(bookId: String) {
        favoriteBookViewModel.favoriteBooks.observe(this) { books ->
            isFavorite = books.any { it.id == bookId }
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        favoriteButton.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_outline
        )
    }

    //adds the fav book
    private fun addBookToFavorites(book: FavoriteBook) {
        lifecycleScope.launch {
            favoriteBookViewModel.addFavoriteBook(book)
            runOnUiThread {
                Toast.makeText(this@BookDetailActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //removes from the fav
    private fun removeFromFavorites(book: FavoriteBook) {
        lifecycleScope.launch {
            favoriteBookViewModel.removeFavoriteBook(book)
            runOnUiThread {
                Toast.makeText(this@BookDetailActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            bookTitle.text = message
            bookAuthor.text = "N/A"
            bookPublishedDate.text = "N/A"
            bookDescription.text = "No description available"
            loadingSpinner.visibility = View.GONE
        }
    }
}