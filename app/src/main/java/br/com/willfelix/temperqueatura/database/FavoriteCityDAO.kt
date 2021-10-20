package br.com.willfelix.temperqueatura.database

import androidx.room.*
import br.com.willfelix.temperqueatura.models.FavoriteCity

@Dao
interface FavoriteCityDAO {

    @Query("SELECT * FROM favorite_city")
    fun all(): List<FavoriteCity>

    @Query("SELECT * FROM favorite_city WHERE id = :id ")
    fun find(id: Int): FavoriteCity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteCity: FavoriteCity)

    @Delete
    fun delete(favoriteCity: FavoriteCity)


}