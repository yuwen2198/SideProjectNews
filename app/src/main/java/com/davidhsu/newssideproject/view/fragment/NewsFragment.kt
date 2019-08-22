package com.davidhsu.newssideproject.view.fragment

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.RecycleViewAdapter
import com.davidhsu.newssideproject.api.NewsApi
import com.davidhsu.newssideproject.api.RetrofitComponent
import com.davidhsu.newssideproject.api.model.Article
import com.davidhsu.newssideproject.api.model.ResponseNewsData
import com.davidhsu.newssideproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.view.*
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class NewsFragment : Fragment() {

    companion object {
        const val responseSuccess = "ok"
        const val newsApiKey = "7b370eccef7d4eca8d6af86e3ad40ea5"
    }

    private var data : List<Article> = ArrayList()

    private var disposable : Disposable? = null

    private val adapter : RecycleViewAdapter by lazy {
        RecycleViewAdapter(data,activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false).apply {
            newsRV.layoutManager = LinearLayoutManager(activity)
            newsRV.adapter = adapter
        }
    }

    private fun initData() {
        val newsApi = RetrofitComponent.getInstance().create(NewsApi::class.java)
        initDataWithCoroutine(newsApi)
    }

    /**
     * retrofit
     */
    private fun initDataWithRetrofit(newsApi: NewsApi) {
        newsApi.getCompositeNews("tw",newsApiKey).enqueue(object : Callback<ResponseNewsData> {
            override fun onFailure(call: Call<ResponseNewsData>, t: Throwable) {
                LogUtil.e("error , failReason : ${t.message}")
            }

            override fun onResponse(call: Call<ResponseNewsData>, response: Response<ResponseNewsData>) {

                val body = response.body()
                body?.let { ResponseNewsData ->
                    if (ResponseNewsData.status == responseSuccess) {
                        data =  ResponseNewsData.articles
                        adapter.setData(data)
                        LogUtil.d("Success , data size = ${data.size}")
                    } else {
                        LogUtil.e("Success , status != ok && status = ${data.size}")
                    }
                }
            }
        })
    }

    /**
     * RxJava + retrofit
     */
    private fun initDataWithRXAndRetrofit(newsApi: NewsApi) {
        disposable = newsApi.getCompositeNewsWithRx("tw", newsApiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ responseData ->

                if (responseData.status == responseSuccess) {
                    data =  responseData.articles
                    adapter.setData(data)
                    LogUtil.d("Success , data size = ${data.size}")
                } else {
                    LogUtil.e("Success , status != ok && status = ${data.size}")
                }

            } ,{ error ->
                LogUtil.e("error , failReason : $error")

            },{
                Toast.makeText(activity,"on finish",Toast.LENGTH_SHORT).show()
            })
    }

    /**
     * globalScope.join() should be called in suspend function
     * globalScope.start() anyWhere
     */
    private fun initDataWithCoroutine(newsApi: NewsApi) {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response  = newsApi.getCompositeNewsWithCorutine("tw",newsApiKey).await()
                handleSuccessResponse(response)
            } catch (e: Exception) {
                Toast.makeText(activity,"Exception = ${e.message}",Toast.LENGTH_SHORT).show()
                LogUtil.e("error , Exception = ${e.message}")
            }
        }
    }

    /**
     * Coroutine suspend (not success , )
     */
    private fun initDataWithCoroutineSuspend(newsApi: NewsApi) {
        //Coroutine suspend (not success , )
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response  = newsApi.getCompositeNewsWithSuspend("tw",newsApiKey)
                handleSuccessResponseWithSuspend(response)
            } catch (e: Exception) {
                Toast.makeText(activity,"Exception = ${e.message}",Toast.LENGTH_SHORT).show()
                LogUtil.e("error , Exception = ${e.message}")
            }
        }
    }

    private fun handleSuccessResponse(response: Response<ResponseNewsData>) {
        val body = response.body()
        body?.let {
            if (body.status == responseSuccess) {
                data = body.articles
                adapter.setData(data)
                LogUtil.d("Success , data size = ${data.size}")
            } else {
                LogUtil.e("Success , status != ok && status = ${data.size}")
            }
        }
    }

    private fun handleSuccessResponseWithSuspend(response: ResponseNewsData) {
        response.let {
            if (response.status == responseSuccess) {
                data = response.articles
                adapter.setData(data)
                LogUtil.d("Success , data size = ${data.size}")
            } else {
                LogUtil.e("Success , status != ok && status = ${data.size}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.let {
            disposable?.dispose()
        }
    }
}