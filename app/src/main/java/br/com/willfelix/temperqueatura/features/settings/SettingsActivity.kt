package br.com.willfelix.temperqueatura.features.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.com.willfelix.temperqueatura.R
import br.com.willfelix.temperqueatura.features.temperatures.WeatherViewModel
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val celsius = viewModel.isCelsius()
        radio_celsius.isChecked = celsius
        radio_fahreheint.isChecked = !celsius

        val isEnglish = viewModel.isEnglish()
        radio_lang_en.isChecked = isEnglish
        radio_lang_pt.isChecked = !isEnglish

        btn_save.setOnClickListener {

            viewModel.saveUnit(radio_celsius.isChecked)
            viewModel.saveLang(radio_lang_en.isChecked)

            finish()
        }
    }
}
