package com.example.bookapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapp.FavoriteBook
import com.example.bookapp.R

//adapter to list the fav books in the recyclerview
class FavBookAdapter(
    private var books: MutableList<FavoriteBook>,
    private val onDeleteClicked: (FavoriteBook) -> Unit
) : RecyclerView.Adapter<FavBookAdapter.FavoriteBookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_book, parent, false)
        return FavoriteBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteBookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)

        //clicklistener for delete button which deletes the fav book
        holder.itemView.findViewById<ImageView>(R.id.deleteButton).setOnClickListener {
            onDeleteClicked(book)
        }
    }

    override fun getItemCount() = books.size

// Updates the adapter with a new list of favorite books.
    fun updateBooks(newBooks: List<FavoriteBook>) {
        books.clear() //clear list
        books.addAll(newBooks) //add fav books
        notifyDataSetChanged() // notify recyclerview of change
    }

    class FavoriteBookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.bookTitle)
        private val authorTextView: TextView = view.findViewById(R.id.bookAuthor)
        private val bookCoverImage: ImageView = view.findViewById(R.id.bookCoverImage)

        //bind book data to ui
        fun bind(book: FavoriteBook) {
            titleTextView.text = book.title
            authorTextView.text = book.author

            //Loading image
            book.thumbnail?.let { url ->
                Glide.with(itemView.context)
                    .load(url)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(bookCoverImage)
            } ?: run {
                bookCoverImage.setImageResource(R.drawable.ic_error) //incase of error shows this icon
            }
        }
    }
}