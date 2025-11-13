package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places_table ORDER BY dataCriacao DESC")
    fun getAllPlaces(): Flow<List<Place>>

    @Query("SELECT * FROM places_table WHERE id = :placeId")
    fun getPlaceById(placeId: String): Flow<Place?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(place: Place)

    @Delete
    suspend fun delete(place: Place)

    @Query("DELETE FROM places_table")
    suspend fun deleteAll()
}