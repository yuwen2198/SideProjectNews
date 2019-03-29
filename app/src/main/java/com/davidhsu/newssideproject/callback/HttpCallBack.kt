package com.davidhsu.newssideproject.callback

import com.davidhsu.newssideproject.api.model.ResponseNewsData

/**
 *
 * @author : DavidHsu on 2019/03/26
 *
 */
interface HttpCallBack {
    fun onSuccess(responseNewsData: ResponseNewsData)
    fun onFail(failReason: String)
}