package com.davidhsu.newssideproject.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.api.model.Location
import kotlinx.android.synthetic.main.fragment_weather_recycler_header.view.*

/**
 * @author : david.hsu on 2019/8/29
 */
class WeatherRecyclerViewAdapter(private val activity: Activity?, private var location: Location) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    companion object {
        const val HEADER_TYPE = 0
        const val WEATHER_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == WEATHER_TYPE) {
            val view = layoutInflater.inflate(R.layout.fragment_weather_recycler_item, parent, false)
            WeatherRecycleViewViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.fragment_weather_recycler_header, parent, false)
            WeatherHeaderViewHolder(view)
        }
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherRecycleViewViewHolder) {
            weatherItemView(holder , position)
        } else if (holder is WeatherHeaderViewHolder) {
            weatherHeaderView(holder)
        }
    }

    private fun weatherItemView(holder: WeatherRecycleViewViewHolder, position: Int) {
        var statusCode = String()
        for (i in location.weatherElement.indices) {
            if (location.weatherElement[i].elementName == "Wx") {
                holder.itemView.weatherStatus.text = location.weatherElement[i].time[position].parameter.parameterName
                statusCode = location.weatherElement[i].time[position].parameter.parameterValue
                holder.itemView.startTime.text = location.weatherElement[i].time[position].startTime
                holder.itemView.endTime.text = location.weatherElement[i].time[position].endTime
            }
        }

        for (i in location.weatherElement.indices) {
            if (location.weatherElement[i].elementName == "MaxT") {
                holder.itemView.maxTemp.text = location.weatherElement[i].time[position].parameter.parameterName
            }
        }

        for (i in location.weatherElement.indices) {
            if (location.weatherElement[i].elementName == "MinT") {
                holder.itemView.miniTemp.text = location.weatherElement[i].time[position].parameter.parameterName
            }
        }
        Glide.with(activity).load(setDrawable(statusCode)).into(holder.itemView.cloudImage)
    }

    private fun weatherHeaderView(holderWeather: WeatherHeaderViewHolder) {
        holderWeather.itemView.location.text = location.locationName
        var statusCode = String()
        for (position in location.weatherElement.indices) {
            if (location.weatherElement[position].elementName == "Wx") {
                holderWeather.itemView.weatherStatus.text = location.weatherElement[position].time[0].parameter.parameterName
                holderWeather.itemView.startTime.text = location.weatherElement[position].time[0].startTime
                holderWeather.itemView.endTime.text = location.weatherElement[position].time[0].endTime
                statusCode = location.weatherElement[position].time[0].parameter.parameterValue
            }
        }

        for (position in location.weatherElement.indices) {
            if (location.weatherElement[position].elementName == "MaxT") {
                holderWeather.itemView.maxTemp.text = location.weatherElement[position].time[0].parameter.parameterName
            }
        }

        for (position in location.weatherElement.indices) {
            if (location.weatherElement[position].elementName == "MinT") {
                holderWeather.itemView.miniTemp.text = location.weatherElement[position].time[0].parameter.parameterName
            }
        }
        Glide.with(activity).load(setDrawable(statusCode)).into(holderWeather.itemView.cloudImage)
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) HEADER_TYPE else WEATHER_TYPE

    fun setData(location: Location) {
        this.location = location
        notifyDataSetChanged()
    }

    private fun setDrawable(weatherStatus: String): Int = when(weatherStatus) {
            "1" -> R.drawable.ic_sunny
            "2" -> R.drawable.ic_sunny_cloudy
            "3" -> R.drawable.ic_sunny_cloudy
            "4" -> R.drawable.ic_cloudy
            "8" -> R.drawable.ic_cloudy_rain
            "15" -> R.drawable.ic_cloudy_rain_light
            "19" -> R.drawable.ic_sunny_cloudy_rain
            "22" -> R.drawable.ic_cloudy_rain_light
            else -> R.drawable.ic_question
        }

    inner class WeatherRecycleViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class WeatherHeaderViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
}