package com.davidhsu.newssideproject.view.activity

import android.content.Intent
import android.os.Bundle
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.utils.LogUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_lunch.*

/**
 *
 * @author : DavidHsu on 2019/04/01
 *
 */

class SplashActivity : BaseActivity() {

    companion object{
        private const val REQUEST_GOOGLE_SIGN_IN = 1000
    }

    private val mLoginManager by lazy {
        LoginManager.getInstance()
    }

    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_lunch)

        val clientId = getString(R.string.client_id)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(clientId)
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleLogin.setOnClickListener { googleLogin() }
        fbLogin.setOnClickListener { faceBookLogin() }

    }

    private fun googleLogin() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, REQUEST_GOOGLE_SIGN_IN)
    }

    private fun faceBookLogin() {
        mLoginManager.logInWithReadPermissions(this, listOf("email", "public_profile"))
        mLoginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(loginResult: LoginResult) {
                LogUtil.d("Call Facebook SDK login")
                val accessToken = AccessToken.getCurrentAccessToken()
                val request = GraphRequest.newMeRequest(
                    accessToken
                ) { `object`, response ->
                    val fbId = `object`.getString("id")
                    val fbMail = `object`.getString("email")
                    val fbToken = loginResult.accessToken.token
                    val fbName = `object`.getString("name")
                    val userProfilePicture = "https://graph.facebook.com/$fbId/picture?type=large"
                    LogUtil.d("Facebook mail = $fbMail , Facebook token = $fbToken , Facebook name = $fbName ")

                    intentToMainActivity(fbName, fbMail, userProfilePicture)
                }

                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,picture")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                LogUtil.i("Facebook login cancel")
            }

            override fun onError(error: FacebookException) {
                LogUtil.e("FacebookException : ${error.message}")
            }

        })
    }

    private fun intentToMainActivity(name: String?, email: String?, userProfilePicture: String?) {
        val intent = Intent(this,MainActivity::class.java).apply {
            putExtra("name", name)
            putExtra("email", email)
            putExtra("photoUrl", userProfilePicture)
        }
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GOOGLE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                LogUtil.d("Google SDK login success !!")
                val acct = GoogleSignIn.getLastSignedInAccount(baseContext)
                acct?.let {
                    intentToMainActivity(acct.givenName, acct.email, acct.photoUrl.toString())
                    LogUtil.d("Google name = ${acct.givenName} , Google mail = ${acct.email} , Google photo = ${acct.photoUrl.toString()}")
                } ?: run {
                    LogUtil.e("Google SDK GoogleSignInAccount object is null and can't get info !!")
                }
            } else {
                LogUtil.e("Google SDK sign in fail !! , code = ${result.status}")
            }
        } else {
            //FB
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }
}