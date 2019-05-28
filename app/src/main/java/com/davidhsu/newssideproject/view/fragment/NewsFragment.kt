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
import com.davidhsu.newssideproject.callback.HttpCallBack
import com.davidhsu.newssideproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.view.*
import io.reactivex.disposables.Disposable
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
        const val responseStatus = "ok"

        const val responseSuccess = "ok"
        const val newsApiKey = "7b370eccef7d4eca8d6af86e3ad40ea5"
    }

    private var data : List<Article> = ArrayList()

    private lateinit var disposable : Disposable

    private val adapter : RecycleViewAdapter by lazy {
        RecycleViewAdapter(data,activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        view.newsRV.layoutManager = LinearLayoutManager(activity)
        view.newsRV.adapter = adapter

        return view
    }

    private fun initData() {

        val newsApi : NewsApi = RetrofitComponent.getInstance().create(NewsApi::class.java)

        //retrofit
        newsApi.getCompositeNews("tw",newsApiKey).enqueue(object : Callback<ResponseNewsData> {
            override fun onFailure(call: Call<ResponseNewsData>, t: Throwable) {
                LogUtil.e("error , failReason : ${t.message}")
            }

            override fun onResponse(call: Call<ResponseNewsData>, response: Response<ResponseNewsData>) {

                val body = response.body()
                body?.let { ResponseNewsData ->
                    if (ResponseNewsData.status == responseStatus) {
                        data =  ResponseNewsData.articles
                        adapter.setData(data)
                        LogUtil.d("Success , data size = ${data.size}")
                    } else {
                        LogUtil.e("Success , status != ok && status = ${data.size}")
                    }
                }

            }

        })

        // RxJava + retrofit
//        disposable = newsApi.getCompositeNewsWithRx("tw", newsApiKey)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe ({ responseData ->
//
//                if (responseData.status == responseStatus) {
//                    data =  responseData.articles
//                    adapter.setData(data)
//                    LogUtil.d("Success , data size = ${data.size}")
//                } else {
//                    LogUtil.e("Success , status != ok && status = ${data.size}")
//                }
//
//            } ,{ error ->
//                LogUtil.e("error , failReason : $error")
//
//            },{
//                Toast.makeText(activity,"on finish",Toast.LENGTH_SHORT).show()
//            })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}