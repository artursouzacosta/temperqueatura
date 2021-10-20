package br.com.willfelix.temperqueatura.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.willfelix.temperqueatura.models.City
import br.com.willfelix.temperqueatura.models.WeatherResult
import br.com.willfelix.temperqueatura.utils.Const
import br.com.willfelix.temperqueatura.utils.RetrofitManager
import org.jetbrains.anko.doAsync

class WeatherRepository(app: Application) {

    // live data
    private val weather = MutableLiveData<WeatherResult?>()
    private val group = MutableLiveData<WeatherResult?>()

    // weather api
    private val weatherService by lazy {
        RetrofitManager.getWeatherService()
    }

    // shared preferences
    private val sp by lazy {
        app.getSharedPreferences(Const.SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }


    fun weather() = weather

    fun group() = group

    fun find(query: String): LiveData<WeatherResult?> {
        val unit = getUnit()
        val lang = getLang()

        doAsync {
            val result = weatherService
                .find(query, Const.APP_KEY, unit, lang)
                .execute()
                .body()

            weather.postValue(result)
        }

        return weather
    }

    fun groupRequest(ids: String): LiveData<WeatherResult?> {
        val unit = getUnit()
        val lang = getLang()

        doAsync {
            val result = weatherService
                .group(ids, Const.APP_KEY, unit, lang)
                .execute()
                .body()

            group.postValue(result)
        }

        return group
    }

    fun isCelsius() = sp.getBoolean(Const.PREF_IS_CELSIUS, true)

    fun saveUnit(isCelsius: Boolean) {
        sp.edit().putBoolean(Const.PREF_IS_CELSIUS, isCelsius).apply()
    }

    fun isEnglish() = sp.getBoolean(Const.PREF_IS_ENGLISH, true)

    fun saveLang(isEnglish: Boolean) {
        sp.edit().putBoolean(Const.PREF_IS_ENGLISH, isEnglish).apply()
    }



    private fun getUnit(): String {
        var unit = "imperial"
        if (isCelsius()) unit = "metric"

        return unit
    }

    private fun getLang(): String {
        var lang = "pt"
        if (isEnglish()) lang = "en"

        return lang
    }

}