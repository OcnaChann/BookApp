package com.example.bookapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.bookapp.FavoriteBook
import com.example.bookapp.data.DatabaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Viewmodel for managing the list of favorite books
class FavoriteBookViewmodel(application: Application) : AndroidViewModel(application) {

    private val favoriteBookDao = DatabaseInstance.getDatabase(application).favoriteBookDao()
    private val _favoriteBooks = MutableLiveData<List<FavoriteBook>>() //mutable coz to hold list of fav books
    val favoriteBooks: LiveData<List<FavoriteBook>> get() = _favoriteBooks

    init {
        loadFavoriteBooks() //loads the fav book as soonas viewmodel is initialized
    }

    private fun loadFavoriteBooks() {
        viewModelScope.launch {
            val books = favoriteBookDao.getAllFavoriteBooks()
            Log.d("FavoriteBookViewModel", "Favorite Books Loaded: ${books.size}")
            _favoriteBooks.postValue(books) // post to livedata, postvalue to ensure livedata is updated
        }
    }


    fun addFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteBookDao.insert(book) //insert book into database
            loadFavoriteBooks() // Refresh the list after adding a book
        }
    }

    fun removeFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            favoriteBookDao.delete(book)
            loadFavoriteBooks() // Refresh the list after removing a book
        }
    }
}


