package com.example.bookapp.Screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.adapter.FavBookAdapter
import com.example.bookapp.viewmodel.FavoriteBookViewmodel
import com.example.bookapp.R

class FavoriteFragment : Fragment() {

    private lateinit var favoriteBookAdapter: FavBookAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteBookViewModel: FavoriteBookViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(context)

        favoriteBookAdapter = FavBookAdapter(mutableListOf()) { book ->

            favoriteBookViewModel.removeFavoriteBook(book)
        }

        recyclerView.adapter = favoriteBookAdapter //sets adapter to recyclerview
        //initialize viewmodel
        favoriteBookViewModel = ViewModelProvider(this)[FavoriteBookViewmodel::class.java]

        favoriteBookViewModel.favoriteBooks.observe(viewLifecycleOwner) { books ->

            if (books.isEmpty() && favoriteBookAdapter.itemCount == 0) {  //  show if adapter is also empty
                Toast.makeText(context, "No favorite books found!", Toast.LENGTH_SHORT).show()
            }

            favoriteBookAdapter.updateBooks(books) // Update the adapter with new list of fav books
        }
        return view
    }
}

