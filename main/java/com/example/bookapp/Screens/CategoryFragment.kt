package com.example.bookapp.Screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.viewmodel.BookViewModel
import com.example.bookapp.R
import com.example.bookapp.adapter.CategoryAdapter

class CategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private val bookViewModel: BookViewModel by viewModels()
    private var category: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        // Initializing recyclerview
        recyclerView = view.findViewById(R.id.catrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize Adapter with empty list
        categoryAdapter = CategoryAdapter(emptyList()) { book ->
            // Navigate to book detail activity
            val intent = Intent(activity, BookDetailActivity::class.java)
            intent.putExtra("BOOK_TITLE", book.volumeInfo.title)
            startActivity(intent)
        }
        recyclerView.adapter = categoryAdapter

        // Get category name from arguments
        category = arguments?.getString("CATEGORY_KEY")

        category?.let {
            // Fetch books based on the category
            bookViewModel.fetchBooks(it)
            bookViewModel.books.observe(viewLifecycleOwner) { books ->
                categoryAdapter.updateBooks(books)
            }
        }

        return view
    }
}