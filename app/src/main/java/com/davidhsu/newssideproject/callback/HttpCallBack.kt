package com.davidhsu.newssideproject.callback

/**
 *
 * @author : DavidHsu on 2019/03/26
 *
 */
interface HttpCallBack {
    fun onSuccess(obj: Any)
    fun onFail(failReason: String)
}