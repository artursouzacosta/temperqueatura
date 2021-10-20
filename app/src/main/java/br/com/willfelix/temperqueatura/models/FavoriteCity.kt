package br.com.willfelix.temperqueatura.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_city")
class FavoriteCity(
    @PrimaryKey
    val id: Int,
    val name: String
)