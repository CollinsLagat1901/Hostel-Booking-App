package com.example.hostelbookingapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentStatusActivity : AppCompatActivity() {

    // Sample booking ID. Replace this with the actual booking ID obtained during booking process.
    private val bookingId = "123456789"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_status)

        // Assume you have a TextView in your layout to display the payment status
        val paymentStatusTextView: TextView = findViewById(R.id.paymentstatus_text_view)

        // Call method to check payment status based on payment method (M-Pesa or PayPal)
        checkPaymentStatus(bookingId) { paymentStatus ->
            // Update UI with payment status
            paymentStatusTextView.text = "Payment Status: $paymentStatus"
        }
    }

    private fun checkPaymentStatus(bookingId: String, callback: (paymentStatus: String) -> Unit) {
        // Here you would make a request to your backend to check the payment status based on the booking ID
        // You would typically send the booking ID to your backend and get the payment status in response
        // This is just a sample implementation, replace it with your actual implementation

        // Assume you have a method in your backend to check payment status, and it returns a string
        // You would call that method here and pass the result to the callback function

        // Sample implementation (replace with your actual implementation)
        val paymentStatus = getPaymentStatusFromBackend(bookingId)

        // Invoke the callback function with the payment status obtained from backend
        callback(paymentStatus)
    }

    // Sample method to simulate getting payment status from backend
    private fun getPaymentStatusFromBackend(bookingId: String): String {
        // This is a placeholder method to simulate getting payment status from backend
        // In your actual implementation, you would make a request to your backend to get the payment status
        // and return the actual payment status obtained from backend
        // Replace this with your actual implementation
        // Assume your backend returns "Paid" if payment is successful and "Pending" otherwise
        // You would replace this logic with your actual logic to check payment status
        // For M-Pesa payments, you would check the payment status from M-Pesa API response
        // For PayPal payments, you would check the payment status from PayPal API response

        // For demo purposes, let's assume the payment status is "Paid"
        return "Paid"
    }
}