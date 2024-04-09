package com.example.hostelbookingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {

    private lateinit var allocation: Button
    private lateinit var payment: Button
    private lateinit var confirmation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        allocation = findViewById(R.id.allocation)
        confirmation = findViewById(R.id.confirmation)
        payment = findViewById(R.id.payment)

        allocation.setOnClickListener {
            startActivity(Intent(this@HomePage, AllocationActivity::class.java))
        }

        confirmation.setOnClickListener {
            startActivity(Intent(this@HomePage, ConfirmationActivity::class.java))
        }

        payment.setOnClickListener {
            startActivity(Intent(this@HomePage, PaymentStatusActivity::class.java))
        }
    }
}
