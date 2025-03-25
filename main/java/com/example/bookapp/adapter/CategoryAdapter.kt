package com.example.bookapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapp.BookItem
import com.example.bookapp.R

class CategoryAdapter(
    private var bookList: List<BookItem>,
    private val onItemClick: (BookItem) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.bookTitle)
        val author: TextView = itemView.findViewById(R.id.bookAuthor)
        val imageView: ImageView = itemView.findViewById(R.id.bookCoverImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val book = bookList[position]

        // Set title and author
        holder.title.text = book.volumeInfo.title
        holder.author.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author"

        // Log the image URL
        val imageUrl = book.volumeInfo.imageLinks?.thumbnail
        Log.d("Glide", "Loading image: $imageUrl")

        // Load the image using Glide
        imageUrl?.let { url ->
            Glide.with(holder.itemView.context)
                .load(url)
                .placeholder(R.drawable.ic_placeholder) // Placeholder image while loading
                .error(R.drawable.ic_error) // Fallback image if loading fails
                .into(holder.imageView)
        } ?: run {
            // If the URL is null or empty
            holder.imageView.setImageResource(R.drawable.ic_error)
            Log.e("Glide", "Image URL is null or empty.")
        }

        // Set click listener
        holder.itemView.setOnClickListener {
            onItemClick(book)
        }
    }

    override fun getItemCount(): Int = bookList.size

    fun updateBooks(newBooks: List<BookItem>) {
        bookList = newBooks
        notifyDataSetChanged()
    }
}


