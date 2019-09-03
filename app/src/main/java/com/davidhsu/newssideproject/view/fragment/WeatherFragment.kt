package com.davidhsu.newssideproject.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.WeatherRecyclerViewAdapter
import com.davidhsu.newssideproject.api.RetrofitComponent
import com.davidhsu.newssideproject.api.WeatherApi
import com.davidhsu.newssideproject.api.model.Location
import com.davidhsu.newssideproject.utils.LogUtil
import com.davidhsu.newssideproject.viewmodel.WeatherFragmentViewModel
import kotlinx.android.synthetic.main.fragment_weather.view.*

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class WeatherFragment : Fragment() {

    private var currentLocation = String()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WeatherFragmentViewModel::class.java)
    }

    private val adapter by lazy {
        WeatherRecyclerViewAdapter(activity, Location("", ArrayList()))
    }

    override fun onStart() {
        super.onStart()
        if (isAdded) {
            currentLocation = arguments?.getString("location").toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val weatherApi = RetrofitComponent.getWeatherInstance().create(WeatherApi::class.java)
        viewModel.getDataWithRXAndRetrofit(weatherApi, currentLocation)
        setUpObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false).apply {
            weatherRV.layoutManager = LinearLayoutManager(activity)
            weatherRV.adapter = adapter
        }
    }

    private fun setUpObserver() {
        viewModel.weatherData.observe(this, Observer { ResponseWeatherData ->
            LogUtil.d("ResponseWeatherData = ${ResponseWeatherData?.success}")
            adapter.setData(ResponseWeatherData?.records!!.location[0])
        })

        viewModel.weatherDataFail.observe(this, Observer {
            LogUtil.e("error = ${it.toString()}")
        })

    }

}