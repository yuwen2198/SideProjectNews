package com.davidhsu.newssideproject.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.view.LoadingDialog

/**
 *
 * @author : DavidHsu on 2019/04/01
 *
 */
open class BaseActivity : AppCompatActivity() {

    private val loadingDialog : LoadingDialog by lazy {
        LoadingDialog(this,getString(R.string.loading),R.drawable.ic_dialog_loading)
    }

    protected fun showProgressBar() {
        loadingDialog.show()
    }

    protected fun showProgressBar(msg : String) {
        loadingDialog.setMessage(msg)
        loadingDialog.show()
    }

    protected fun cancelProgressBar() {
        loadingDialog.dismiss()
    }

}