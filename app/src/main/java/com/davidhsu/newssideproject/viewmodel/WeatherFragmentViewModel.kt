package com.davidhsu.newssideproject.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.davidhsu.newssideproject.api.WeatherApi
import com.davidhsu.newssideproject.api.model.ResponseWeatherData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author : david.hsu on 2019/8/29
 */
class WeatherFragmentViewModel(application: Application) : AndroidViewModel(application)  {

    companion object{
        private const val Authorization = "CWB-0E51A967-9394-40AF-82AA-CA584DAAFE03"
    }

    private var disposable: Disposable? = null

    var weatherData: MutableLiveData<ResponseWeatherData> = MutableLiveData()
    var weatherDataFail: MutableLiveData<Throwable> = MutableLiveData()

    /**
     * RxJava + retrofit
     */
    fun getDataWithRXAndRetrofit(weatherApi: WeatherApi, city: String) {
        disposable = weatherApi.getCompositeNews("臺北市", Authorization)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ responseData ->
                weatherData.value = responseData
            } ,{ error ->
                weatherDataFail.value = error
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.let {
            disposable?.dispose()
        }
    }

}