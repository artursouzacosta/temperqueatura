package br.com.willfelix.temperqueatura.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.willfelix.temperqueatura.database.AppRoom
import br.com.willfelix.temperqueatura.models.City
import br.com.willfelix.temperqueatura.models.FavoriteCity
import br.com.willfelix.temperqueatura.models.WeatherResult
import br.com.willfelix.temperqueatura.utils.Const
import br.com.willfelix.temperqueatura.utils.RetrofitManager
import org.jetbrains.anko.doAsync

class FavoriteRepository(app: Application) {

    // live data
    private val favorites = MutableLiveData<List<FavoriteCity>?>()

    // dao room
    private val dao by lazy {
        AppRoom.instance(app.applicationContext).getFavoriteCityDAO()
    }

    fun all(): LiveData<List<FavoriteCity>?> {
        doAsync {
            favorites.postValue(dao.all())
        }

        return favorites
    }

    fun add(city: City): FavoriteCity {
        val favorite = FavoriteCity(city.id, city.name)

        doAsync {
            dao.add(favorite)
        }

        return favorite
    }

    fun remove(city: City): FavoriteCity {
        val favorite = FavoriteCity(city.id, city.name)

        doAsync {
            dao.delete(favorite)
        }

        return favorite
    }
}