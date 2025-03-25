package com.example.bookapp
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.*
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Database
import androidx.room.RoomDatabase

// FavoriteBook Data Model
@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val publishedDate: String,
    val description: String?,
    val thumbnail: String?
)

// FavoriteBookDao (Data Access Object)
@Dao
interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteBook: FavoriteBook)

    @Query("SELECT * FROM favorite_books")
    suspend fun getAllFavoriteBooks(): List<FavoriteBook>

    @Delete
    suspend fun delete(favoriteBook: FavoriteBook)
}

// Room Database class
@Database(entities = [FavoriteBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao
}
