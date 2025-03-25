package com.example.bookapp.data

import android.content.Context
import androidx.room.Room
import com.example.bookapp.AppDatabase
//singleton object to provide single instance for database
object DatabaseInstance {
    private var database: AppDatabase? = null

    //gettinf the datbase instance
    fun getDatabase(context: Context): AppDatabase {
        //checks if data is created already
        if (database == null) {
            //intializes databse using room
            database = Room.databaseBuilder(
                context.applicationContext,  //for memory leak
                AppDatabase::class.java,//define data class
                "favorite_books_db" //name defined
            ).build()
        }
        return database as AppDatabase
    }
}
