package br.com.willfelix.temperqueatura.features.temperatures

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.willfelix.temperqueatura.R
import br.com.willfelix.temperqueatura.features.settings.SettingsActivity
import br.com.willfelix.temperqueatura.models.City
import kotlinx.android.synthetic.main.activity_main.*

class WeatherActivity : AppCompatActivity(), WeatherAdapter.CityCallback {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    private val adapter = WeatherAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        doSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.menu_item_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }


    private fun initUI() {

        recycler_weather.layoutManager = LinearLayoutManager(this)
        recycler_weather.adapter = adapter

        edit_search.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) &&
                (i == KeyEvent.KEYCODE_ENTER)) {

                btn_search.performClick()
                return@setOnKeyListener true
            }

            return@setOnKeyListener true
        }

        btn_search.setOnClickListener {

            // hide keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.applicationWindowToken, 0)

            // check connectivity
            val connectivityManager= getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            val isDeviceConnected = networkInfo!=null && networkInfo.isConnected
            if ( !isDeviceConnected ) {
                Toast.makeText(this, "Device is not connected to internet.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doSearch()
        }

    }


    private fun initObservers() {
        viewModel.favorites().observe(this, Observer { result ->
            result?.let { list ->

                adapter.setFavorites( ArrayList(list) )

                val arr = list.map { it.id }.toTypedArray()
                val ids = TextUtils.join(",", arr)
                viewModel.groupRequest(ids)

            }
        })

        viewModel.group().observe(this, Observer {
            adapter.updateItems(it?.list, viewModel.isEnglish())
            paintUIBySearch(it?.list)
        })

        viewModel.weather().observe(this, Observer { result ->

            var list: List<City>? = null
            result?.let { list = it.list }
            adapter.updateItems(list, viewModel.isEnglish())

            paintUIBySearch(list)

        })
    }

    private fun doSearch() {

        progressBar.visibility = View.VISIBLE
        img_no_result.visibility = View.GONE
        recycler_weather.visibility = View.GONE

        val query = edit_search.text.toString()
        if (query.isEmpty()) {
            viewModel.favorites()
            return
        }

        viewModel.find(query)

    }

    private fun paintUIBySearch(list: List<City>?) {
        progressBar.visibility = View.GONE

        if (list != null)
            recycler_weather.visibility = View.VISIBLE
        else
            img_no_result.visibility = View.VISIBLE
    }


    override fun onItemClick(city: City, isFavorite: Boolean) {

        if (isFavorite) {
            val favorite = viewModel.removeFavorite(city)
            adapter.removeFavorite(favorite)
            return
        }

        val favorite = viewModel.addFavorite(city)
        adapter.addFavorite(favorite)

    }

}
