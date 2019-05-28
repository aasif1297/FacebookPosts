package com.example.facebookposts.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.facebookposts.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"
    private var accessToken = AccessToken.getCurrentAccessToken()
    private lateinit var permissions: MutableList<String>
    private var isLoggedIn = accessToken != null && !accessToken.isExpired
    private lateinit var fbLoginButton : LoginButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create();
        permissions = ArrayList()
        permissions.add("public_profile")
        permissions.add("email")
        login_button.setReadPermissions(permissions)
        login_button.registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                /* Intent homeIntent = new Intent(LoginActivity.this,HomeActivity.class);

                startActivity(homeIntent);
                LoginActivity.this.finish();*/
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                val bundle = Bundle()
                bundle.putString("accessToken",result.accessToken?.token)
                intent.putExtras(bundle)
                startActivity(intent)

                Log.d("fbLogin", result.accessToken.userId)

            }

            override fun onCancel() {
                Log.w("fbLoginCancel", "Cancelled")
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity, "${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("fbLoginError", error.toString())
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
