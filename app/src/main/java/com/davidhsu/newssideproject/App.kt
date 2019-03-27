package com.davidhsu.newssideproject

import android.app.Application
import com.davidhsu.newssideproject.utils.LogUtil
import kotlin.properties.Delegates

/**
 *
 * @author : DavidHsu on 2019/03/27
 *
 */
class App : Application() {

    companion object {
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.isDebuggable = true
    }

}