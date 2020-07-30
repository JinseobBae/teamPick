package com.example.teampick.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.teampick.MainActivity
import com.example.teampick.R


class IntroActivity : AppCompatActivity() {
    val DURATION:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }, DURATION)
    }
}