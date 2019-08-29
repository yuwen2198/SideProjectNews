package com.davidhsu.newssideproject.api

import com.davidhsu.newssideproject.api.model.ResponseWeatherData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author : david.hsu on 2019/8/29
 */
interface WeatherApi {

    @GET("rest/datastore//F-C0032-001?")
    fun getCompositeNews(@Query("locationName") city: String, @Header("Authorization") Authorization: String): Observable<ResponseWeatherData>

}