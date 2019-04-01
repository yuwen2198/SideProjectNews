package com.davidhsu.newssideproject.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.api.ApiComponent
import com.davidhsu.newssideproject.api.model.Article
import com.davidhsu.newssideproject.api.model.ResponseNewsData
import com.davidhsu.newssideproject.callback.HttpCallBack
import com.davidhsu.newssideproject.utils.LogUtil
import com.davidhsu.newssideproject.view.fragment.NewsFragment

/**
 *
 * @author : DavidHsu on 2019/04/01
 *
 */

class SplashActivity : BaseActivity() {

    private var data : List<Article> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_lunch)

        showProgressBar()
        sleep()

    }

    private fun sleep (){
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            cancelProgressBar()
            finish()

        }, 3000)

    }

    private fun callApi(){

        val apiComponent = ApiComponent()
        apiComponent.getCompositeNewsInfo(object  : HttpCallBack {
            override fun onSuccess(responseNewsData: ResponseNewsData) {

                if (responseNewsData.status == NewsFragment.responseStatus) {
                    data =  responseNewsData.articles
//                    adapter.setData(data)
                    LogUtil.d("Success , data size = ${data.size}")
                } else {
                    LogUtil.e("Success , status != ok && status = ${data.size}")
                }
            }

            override fun onFail(failReason: String) {
                LogUtil.e("error , failReason : $failReason")
            }

        })

    }

}