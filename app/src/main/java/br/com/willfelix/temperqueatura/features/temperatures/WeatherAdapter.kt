package br.com.willfelix.temperqueatura.features.temperatures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.willfelix.temperqueatura.R
import br.com.willfelix.temperqueatura.models.City
import br.com.willfelix.temperqueatura.models.FavoriteCity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_weather.view.*

class WeatherAdapter(private val callback: CityCallback? = null) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var items: List<City>? = null
    private var isEnglish: Boolean = false

    companion object {
        var favorites: ArrayList<FavoriteCity>? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.row_weather, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { holder.bind(it[position], isEnglish, callback) }
    }

    fun updateItems(items: List<City>?, isEnglish: Boolean) {
        this.items = items
        this.isEnglish = isEnglish
        notifyDataSetChanged()
    }

    fun setFavorites(favorites: ArrayList<FavoriteCity>) {
        WeatherAdapter.favorites = favorites
    }

    fun addFavorite(favoriteCity: FavoriteCity) {
        favorites?.add(favoriteCity)
    }

    fun removeFavorite(favoriteCity: FavoriteCity) {
        favorites?.remove(favoriteCity)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(city: City, isEnglish: Boolean, callback: CityCallback?) {

            var winds = "ventos ${city.wind.speed} m/s | nuvens ${city.clouds.all}%"
            if (isEnglish) winds = "wind ${city.wind.speed} m/s | clouds ${city.clouds.all}%"

            val isFavor = favorites?.find { fc -> fc.id == city.id } != null

            paintStar(isFavor)

            itemView.txt_city.text = city.name

            itemView.txt_temperature.text = "${city.main.temp.toInt()}º"

            itemView.txt_winds.text = winds

            city.weather.first().apply {

                itemView.txt_clouds.text = description

                Glide.with(itemView.context)
                    .load("http://openweathermap.org/img/w/${icon}.png")
                    .into(itemView.img_weather)

            }

            itemView.img_star.setOnClickListener {

                val isFavor = favorites?.find { fc -> fc.id == city.id } != null

                callback?.onItemClick(city, isFavor)

                paintStar(!isFavor)

            }

        }

        private fun paintStar(isFavor: Boolean) {

            if (isFavor) {
                itemView.img_star.setImageResource(android.R.drawable.btn_star_big_on)
            } else {
                itemView.img_star.setImageResource(android.R.drawable.btn_star_big_off)
            }

        }

    }

    interface CityCallback {
        fun onItemClick(city: City, isFavorite: Boolean)
    }

}