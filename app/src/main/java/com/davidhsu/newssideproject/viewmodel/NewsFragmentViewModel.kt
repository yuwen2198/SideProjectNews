package com.davidhsu.newssideproject.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.davidhsu.newssideproject.api.NewsApi
import com.davidhsu.newssideproject.api.model.ResponseNewsData
import com.davidhsu.newssideproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author : david.hsu on 2019/8/22
 */
class NewsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val newsApiKey = "7b370eccef7d4eca8d6af86e3ad40ea5"
    }

    private var disposable: Disposable? = null

    var dataWithCoroutine: MutableLiveData<Response<ResponseNewsData>> = MutableLiveData()
    var dataWithCoroutineFail: MutableLiveData<Exception> = MutableLiveData()
    var dataWithRetrofit: MutableLiveData<Response<ResponseNewsData>> = MutableLiveData()
    var dataWithRetrofitFail: MutableLiveData<Throwable> = MutableLiveData()
    var dataWithRXAndRetrofit: MutableLiveData<ResponseNewsData> = MutableLiveData()
    var dataWithRXAndRetrofitFail: MutableLiveData<Throwable> = MutableLiveData()
    var dataWithRXAndRetrofitFinish: MutableLiveData<Boolean> = MutableLiveData()
    var dataWithCoroutineSuspend: MutableLiveData<ResponseNewsData> = MutableLiveData()
    var dataWithCoroutineSuspendFail: MutableLiveData<Exception> = MutableLiveData()

    /**
     * globalScope.join() should be called in suspend function
     * globalScope.start() anyWhere
     */
    fun getDataWithCoroutine(newsApi: NewsApi) {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response  = newsApi.getCompositeNewsWithCorutine("tw", newsApiKey).await()
                dataWithCoroutine.value = response
            } catch (e: Exception) {
                dataWithCoroutineFail.value = e
            }
        }
    }

    /**
     * retrofit
     */
    fun getDataWithRetrofit(newsApi: NewsApi) {
        newsApi.getCompositeNews("tw",newsApiKey).enqueue(object : Callback<ResponseNewsData> {
            override fun onFailure(call: Call<ResponseNewsData>, t: Throwable) {
                dataWithRetrofitFail.value = t
                LogUtil.e("error , failReason : ${t.message}")
            }

            override fun onResponse(call: Call<ResponseNewsData>, response: Response<ResponseNewsData>) {
                dataWithRetrofit.value = response
            }
        })
    }

    /**
     * RxJava + retrofit
     */
    fun getDataWithRXAndRetrofit(newsApi: NewsApi) {
        disposable = newsApi.getCompositeNewsWithRx("tw", newsApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ responseData ->
                dataWithRXAndRetrofit.value = responseData
            } ,{ error ->
                dataWithRXAndRetrofitFail.value = error
            },{
                dataWithRXAndRetrofitFinish.value = true
            })
    }

    /**
     * Coroutine suspend (not success )
     */
    private fun initDataWithCoroutineSuspend(newsApi: NewsApi) {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response  = newsApi.getCompositeNewsWithSuspend("tw",newsApiKey)
                dataWithCoroutineSuspend.value = response
            } catch (e: Exception) {
                dataWithCoroutineSuspendFail.value = e
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.let {
            disposable?.dispose()
        }
    }

}