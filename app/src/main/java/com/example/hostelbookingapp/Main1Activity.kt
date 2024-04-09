package com.example.hostelbookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Main1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        Handler().postDelayed({
            val mainIntent = Intent(this, MainScreen::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)
    }
}