package com.davidhsu.newssideproject.view.activity

import android.os.Bundle
import android.content.Intent

/**
 *
 * @author : DavidHsu on 2019/09/01
 *
 */
class SplashActivity : BaseActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }
}