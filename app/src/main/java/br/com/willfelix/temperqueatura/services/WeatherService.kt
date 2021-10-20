package br.com.willfelix.temperqueatura.services

import br.com.willfelix.temperqueatura.models.City
import br.com.willfelix.temperqueatura.models.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("find")
    fun find(@Query("q") city: String,
             @Query("appid") appid: String,
             @Query("units") units: String = "default",
             @Query("lang") lang: String = "en"): Call<WeatherResult>

    @GET("group")
    fun group(@Query("id") ids: String,
              @Query("appid") appid: String,
              @Query("units") units: String = "default",
              @Query("lang") lang: String = "en"): Call<WeatherResult>

}