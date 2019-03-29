package com.davidhsu.newssideproject.api

import android.support.annotation.NonNull
import com.davidhsu.newssideproject.callback.HttpCallBack
import com.davidhsu.newssideproject.utils.LogUtil
import com.davidhsu.newssideproject.api.model.ResponseNewsData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 * @author : DavidHsu on 2019/03/26
 *
 */
class ApiComponent {

    companion object {
        const val responseSuccess = "ok"
    }

    var newsApi : NewsApi = RetrofitComponent.getInstance().create(NewsApi::class.java)

    @Synchronized
    fun getCompositeNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getCompositeNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    fun getBusinessNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getBusinessNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    fun getEntertainmentNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getEntertainmentNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    fun getHealthNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getHealthNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    fun getScienceNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getScienceNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    fun getESportsNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getSportsNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    fun getTechnologyNewsInfo(@NonNull httpCallBack: HttpCallBack) {
        val call = newsApi.getTechnologyNews()
        callApi(call,httpCallBack)
    }

    @Synchronized
    private fun callApi(call: Call<ResponseNewsData>, @NonNull httpCallBack: HttpCallBack) {
        call.enqueue(object : Callback<ResponseNewsData>{
            override fun onFailure(call : Call<ResponseNewsData>, throwable : Throwable) {
                httpCallBack.onFail("getNewsInfo fail = $throwable")
                LogUtil.e("getNewsInfo fail = $throwable")
            }

            override fun onResponse(call : Call<ResponseNewsData>, response : Response<ResponseNewsData>) {

                if (response.isSuccessful) {
                    val responseNewsData = response.body()
                    responseNewsData?.let{
                        if (it.status == responseSuccess) {
                            httpCallBack.onSuccess(it)
                        } else {
                            val  meg = it.toString()
                            httpCallBack.onFail("status != OK , meg : $meg")
                            LogUtil.e("status != OK , meg : $meg")
                        }
                    }
                } else {
                    httpCallBack.onFail("API Response fail")
                    LogUtil.e("API Response fail")
                }

            }

        })
    }

}