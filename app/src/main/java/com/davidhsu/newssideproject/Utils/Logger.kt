package com.davidhsu.newssideproject.Utils

import android.util.Log

/**
 *
 * @author : DavidHsu on 2019/03/27
 *
 */
object LogUtil {

    private var className: String = ""
    private var methodName: String = ""
    private var lineNumber: Int = 0
    var isDebuggable = true

    private fun createLog(log: String): String {
        val buffer = StringBuffer().apply {
            append("[")
            append(methodName)
            append(":")
            append(lineNumber)
            append("]")
            append(log)
        }
        return buffer.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (isDebuggable) {
            getMethodNames(Throwable().stackTrace)
            Log.e(className, createLog(message))
        }
    }

    fun i(message: String) {
        if (isDebuggable) {
            getMethodNames(Throwable().stackTrace)
            Log.i(className, createLog(message))
        }
    }

    fun d(message: String) {
        if (isDebuggable) {
            getMethodNames(Throwable().stackTrace)
            Log.d(className, createLog(message))
        }
    }

    fun v(message: String) {
        if (isDebuggable) {
            getMethodNames(Throwable().stackTrace)
            Log.v(className, createLog(message))
        }
    }

    fun w(message: String) {
        if (isDebuggable) {
            getMethodNames(Throwable().stackTrace)
            Log.w(className, createLog(message))
        }
    }

    fun build(vararg content: String): String {
        val sb = StringBuilder()
        for (i in content.indices) {
            if ((i + 1) % 2 != 0) {
                sb.append("(").append(content[i]).append(":")
            } else {
                sb.append(content[i]).append(") ")
            }
        }

        return sb.toString()
    }
}
