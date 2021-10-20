package br.com.willfelix.temperqueatura.utils

import br.com.willfelix.temperqueatura.services.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private val instance = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Const.BASE_URL)
        .build()

    fun getWeatherService(): WeatherService = instance.create(WeatherService::class.java)

}