package com.example.ddoview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)
            finish()
        },1500)
    }
}