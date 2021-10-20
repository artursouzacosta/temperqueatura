package br.com.willfelix.temperqueatura.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.willfelix.temperqueatura.models.FavoriteCity

@Database(entities = [ FavoriteCity::class ], version = 1)
abstract class AppRoom: RoomDatabase() {
    
    abstract fun getFavoriteCityDAO(): FavoriteCityDAO

    companion object {

        private var INSTANCE : AppRoom? = null

        fun instance(context: Context): AppRoom {
            synchronized(AppRoom::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context, AppRoom::class.java, "temperQueAtura")
                            .allowMainThreadQueries()
                            .build()
                }
            }

            return INSTANCE!!
        }

    }

}