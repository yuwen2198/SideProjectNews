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
//        startActivity(Intent(this, LogInActivity::class.java))
        intentToMainActivity("facebook", "david", "david98249339@hotmail.com" , "http://classweb.loxa.edu.tw/clockloxa/pictureshow/174computerwallpaper/161.jpg")
        finish()
    }

    private fun intentToMainActivity(loginType: String, name: String?, email: String?, userProfilePicture: String?) {
        val intent = Intent(this,MainActivity::class.java).apply {
            putExtra("loginType", loginType)
            putExtra("name", name)
            putExtra("email", email)
            putExtra("photoUrl", userProfilePicture)
        }
        startActivity(intent)
        finish()
    }
}