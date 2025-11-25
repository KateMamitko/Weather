package com.example.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.local.model.CityDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {

    @Query("SELECT * FROM city")
    fun getFavoriteCity(): Flow<List<CityDbModel>>

    @Query("SELECT EXISTS (SELECT * FROM city WHERE id=:idCity LIMIT 1)")
    fun observeIsFollowing(idCity: String): Flow<Boolean>

    @Query("DELETE FROM city WHERE id=:cityId")
    suspend fun deleteFavoriteCity(cityId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(cityModel: CityDbModel)
}