package com.davidhsu.newssideproject.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.location.*
import android.widget.Toast
import com.davidhsu.newssideproject.utils.LogUtil
import java.util.*

/**
 * @author : david.hsu on 2019/8/28
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application)  {

    private var latitude = 0.0
    private var longitude = 0.0

    private var addresses: List<Address> = ArrayList()

    var currentLocation: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(activity: Activity) {

        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val criteria = Criteria().apply {
            accuracy = Criteria.ACCURACY_COARSE//低精度，如果設置為高精度，依然獲取不了location。
            isAltitudeRequired = false//不要求海拔
            isBearingRequired = false//不要求方位
            isCostAllowed = true//允許有花費
            powerRequirement = Criteria.POWER_LOW//低功耗
        }

        val locationProvider = locationManager.getBestProvider(criteria, true)
        val location = locationManager.getLastKnownLocation(locationProvider)

        latitude = location.latitude
        longitude = location.longitude
        LogUtil.d("latitude = $latitude , longitude = $longitude")

        val gcd = Geocoder(activity, Locale.getDefault())
        addresses = gcd.getFromLocation(latitude, longitude, 1)
        LogUtil.d("addresses = $addresses")

        if (!checkGPSLocationIsValid()) {
            Toast.makeText(activity,"獲取位置失敗 ， 使用預設值為臺北市", Toast.LENGTH_SHORT).show()
            currentLocation.value = "臺北市"
        }

        if (addresses.isNotEmpty()) {
            val addressObj = addresses[0]
            when {
                addressObj.adminArea != null -> {
                    val cityName = addressObj.adminArea
                    LogUtil.d("CityName = $cityName")
                    currentLocation.value = cityName
                }
                addressObj.subAdminArea != null -> {
                    val cityName = addressObj.subAdminArea
                    LogUtil.d("CityName = $cityName")
                    currentLocation.value = cityName
                }
                else -> {
                    currentLocation.value = "臺北市"
                    Toast.makeText(activity,"獲取位置失敗 ， 使用預設值為臺北市", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            LogUtil.d("fail")
            currentLocation.value = "臺北市"
            Toast.makeText(activity,"獲取位置失敗 ， 使用預設值為臺北市", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkGPSLocationIsValid(): Boolean {
        return (latitude != 0.0 && longitude != 0.0)
    }
}