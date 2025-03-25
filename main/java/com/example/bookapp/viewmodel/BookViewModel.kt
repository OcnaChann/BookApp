package com.example.bookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.ApiService
import com.example.bookapp.BookItem
import com.example.bookapp.RetrofitInstance
import kotlinx.coroutines.launch
//viewmodel to manage book data
class BookViewModel : ViewModel() {
    val book = MutableLiveData<List<BookItem>>(emptyList()) //to update and notify if changes occurs
    val books: LiveData<List<BookItem>> get() = book
    private val apiService: ApiService = RetrofitInstance.api // creating instance to interact with api

    //fetching books from api through search query
    fun fetchBooks(query: String) {
        viewModelScope.launch {           //launching coroutine
            try {
                val response = apiService.getBooks(query, "AIzaSyDg6H8k3IW4N4zwN6Rh87iJDJYFP_yRNRI")
                book.postValue(response.items ?: emptyList()) //updating mutable livedata with fetched list or empty list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
