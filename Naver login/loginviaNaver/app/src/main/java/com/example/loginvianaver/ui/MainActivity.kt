package com.example.loginvianaver.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginvianaver.R
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val clientId = getString(R.string.OAUTH_CLIENT_ID)
        val clientSecret = getString(R.string.OAUTH_CLIENT_SECRET)
        val clientName = getString(R.string.OAUTH_CLIENT_NAME)

        NaverIdLoginSDK.initialize(this, clientId, clientSecret, clientName)
        val btn = findViewById<NidOAuthLoginButton>(R.id.buttonOAuthLoginImg)
        btn.setOAuthLogin(object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Log.e("NaverLogin", "onError: $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.e("NaverLogin", "onFailure: $message")
            }

            override fun onSuccess() {
                Log.d("NaverLogin", "success")
                val intent = Intent(this@MainActivity,HomeActivity::class.java)
                startActivity(intent)

            }
        })
    }


}
