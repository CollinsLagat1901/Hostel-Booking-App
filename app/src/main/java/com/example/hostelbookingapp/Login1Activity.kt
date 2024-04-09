package com.example.hostelbookingapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.hostelbookingapp.databinding.ActivityLogin1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin1Binding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)
        binding = ActivityLogin1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.noAccountTv.setOnClickListener {
            val intent = Intent(this, SignupActivit::class.java)
            startActivity(intent)
        }
        binding.signinBtn.setOnClickListener {


            validateData()
        }
    }

    private val email = ""
    private val name = ""

    private fun validateData() {
        val email = binding.emailEt.text.toString()
        val pass = binding.passET.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "Enter Password..", Toast.LENGTH_SHORT).show()
        } else {
            loginUser()
        }

    }

    private fun loginUser() {
        progressDialog.setMessage("Logging In..")
        progressDialog.show()

        val email = binding.emailEt.text.toString()
        val pass = binding.passET.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
            // Handle successful login
            checkUser()
        }.addOnFailureListener { e ->
            // Handle failed login
            progressDialog.dismiss()
            Toast.makeText(this, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUser() {

        progressDialog.setMessage("Checking User..")
        val firebaseUser =firebaseAuth.currentUser!!

        val ref= FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
                    //get user type e.g. user or admin
                    val userType =snapshot.child("userType").value

                    if(userType == "user"){
                        //its simple user
                        startActivity(Intent(this@Login1Activity, HomePage::class.java))
                        finish()
                    }
                    else if (userType =="Admin"){
                        //its simple admin
                        startActivity(Intent(this@Login1Activity, HomePage::class.java))
                        finish()

                    }
                }override fun onCancelled(error: DatabaseError){

                }
            })

    }


}
