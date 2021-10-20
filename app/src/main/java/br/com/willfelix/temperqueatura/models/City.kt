package br.com.willfelix.temperqueatura.models

data class City(val id: Int = 0,
                val name: String = "",
                val weather: List<Weather>,
                val wind: Wind,
                val main: Main,
                val clouds: Cloud)