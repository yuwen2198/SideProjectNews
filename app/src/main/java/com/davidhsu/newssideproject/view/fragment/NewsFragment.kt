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
import android.widget.Toast
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.RecycleViewAdapter
import com.davidhsu.newssideproject.api.NewsApi
import com.davidhsu.newssideproject.api.RetrofitComponent
import com.davidhsu.newssideproject.api.model.Article
import com.davidhsu.newssideproject.api.model.ResponseNewsData
import com.davidhsu.newssideproject.utils.LogUtil
import com.davidhsu.newssideproject.viewmodel.NewsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_news.view.*
import io.reactivex.disposables.Disposable
import retrofit2.Response

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class NewsFragment : Fragment() {

    companion object {
        const val responseSuccess = "ok"
    }

    private var data: List<Article> = ArrayList()
    private var disposable: Disposable? = null

    private val adapter: RecycleViewAdapter by lazy {
        RecycleViewAdapter(data, activity)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(NewsFragmentViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val newsApi = RetrofitComponent.getInstance().create(NewsApi::class.java)
        viewModel.getDataWithCoroutine(newsApi)
        setUpObserver()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false).apply {
            newsRV.layoutManager = LinearLayoutManager(activity)
            newsRV.adapter = adapter
        }
    }

    private fun setUpObserver() {
        viewModel.dataWithCoroutine.observe(this, Observer { response ->
            handleSuccessResponse(response)
        })

        viewModel.dataWithCoroutineFail.observe(this, Observer { exception ->
            exception?.let {
                Toast.makeText(activity,"Exception = ${exception.message}",Toast.LENGTH_SHORT).show()
                LogUtil.e("error , Exception = ${exception.message}")
            }
        })

        viewModel.dataWithRetrofit.observe(this, Observer { response ->
            val body = response?.body()
            body?.let { ResponseNewsData ->
                if (ResponseNewsData.status == responseSuccess) {
                    data =  ResponseNewsData.articles
                    adapter.setData(data)
                    LogUtil.d("Success , data size = ${data.size}")
                } else {
                    LogUtil.e("Success , status != ok && status = ${data.size}")
                }
            }
        })

        viewModel.dataWithRetrofitFail.observe(this, Observer { throwable ->
            throwable?.let {
                LogUtil.e("error , failReason : ${throwable.message}")
            }
        })

        viewModel.dataWithRXAndRetrofit.observe(this, Observer { responseData ->
            responseData?.let {
                if (responseData.status == responseSuccess) {
                    data =  responseData.articles
                    adapter.setData(data)
                    LogUtil.d("Success , data size = ${data.size}")
                } else {
                    LogUtil.e("Success , status != ok && status = ${data.size}")
                }
            }
        })

        viewModel.dataWithRXAndRetrofitFail.observe(this, Observer { error ->
            LogUtil.e("error , failReason : $error")
        })

        viewModel.dataWithRXAndRetrofitFinish.observe(this, Observer {
            Toast.makeText(activity,"on finish",Toast.LENGTH_SHORT).show()
        })

        viewModel.dataWithCoroutineSuspend.observe(this, Observer { response ->
            response?.let {
                handleSuccessResponseWithSuspend(response)
            }
        })

        viewModel.dataWithCoroutineSuspendFail.observe(this, Observer { exception ->
            exception?.let {
                Toast.makeText(activity,"Exception = ${exception.message}",Toast.LENGTH_SHORT).show()
                LogUtil.e("error , Exception = ${exception.message}")
            }
        })
    }

    private fun handleSuccessResponse(response: Response<ResponseNewsData>?) {
        val body = response?.body()
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