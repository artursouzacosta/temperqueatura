package br.com.willfelix.temperqueatura.features.temperatures

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import br.com.willfelix.temperqueatura.models.City
import br.com.willfelix.temperqueatura.models.FavoriteCity
import br.com.willfelix.temperqueatura.models.WeatherResult
import br.com.willfelix.temperqueatura.repository.FavoriteRepository
import br.com.willfelix.temperqueatura.repository.WeatherRepository

class WeatherViewModel(app: Application): AndroidViewModel(app) {

    private val weatherRepository = WeatherRepository(app)

    private val favoriteRepository = FavoriteRepository(app)


    fun favorites(): LiveData<List<FavoriteCity>?> = favoriteRepository.all()

    fun addFavorite(city: City): FavoriteCity = favoriteRepository.add(city)

    fun removeFavorite(city: City): FavoriteCity = favoriteRepository.remove(city)


    fun weather(): LiveData<WeatherResult?> = weatherRepository.weather()

    fun group(): LiveData<WeatherResult?> = weatherRepository.group()

    fun find(query: String): LiveData<WeatherResult?> = weatherRepository.find(query)

    fun groupRequest(ids: String): LiveData<WeatherResult?> = weatherRepository.groupRequest(ids)


    fun isCelsius(): Boolean = weatherRepository.isCelsius()

    fun saveUnit(isCelsius: Boolean) = weatherRepository.saveUnit(isCelsius)

    fun isEnglish(): Boolean = weatherRepository.isEnglish()

    fun saveLang(isEnglish: Boolean) = weatherRepository.saveLang(isEnglish)


}