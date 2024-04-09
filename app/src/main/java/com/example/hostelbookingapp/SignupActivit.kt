package com.example.hostelbookingapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.hostelbookingapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivit : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //back button
        /* binding.backBtn.setOnClickListener {
             onBackPressed()
         }*/

        /*
                binding.txtSignin.setOnClickListener {
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
        */

        binding.register.setOnClickListener {
            // Input validation
            validateData()
        }
    }

    private fun validateData() {
        val name = binding.nameEt.text.toString()
        val email = binding.emailEt.text.toString()
        val pass = binding.passET.text.toString()
        val confirmPass = binding.confirmPassEt.text.toString()

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email Pattern", Toast.LENGTH_SHORT).show()
        } else if (pass != confirmPass) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
        } else {
            // If all validations pass, create user account
            createUserAccount(email, pass)
        }
    }

    private fun createUserAccount(email: String, pass: String) {
        progressDialog.setMessage("Creating Account..")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                // Handle success
                updateUserInfo(email, binding.nameEt.text.toString()) // Pass email and name to the function
            }
            .addOnFailureListener {
                // Handle failure
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to create account: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo(email: String, name: String) {
        progressDialog.setMessage("Save user Info...")

        // Check if uid is not null
        val uid = firebaseAuth.uid
        if (uid != null) {
            // Timestamp
            val timestamp = System.currentTimeMillis()

            // Set data to add in db
            val hashMap: HashMap<String, Any> = HashMap()
            hashMap["uid"] = uid
            hashMap["email"] = email
            hashMap["name"] = name
            hashMap["profileImage"] = "" // Add empty. will do in profile edit
            hashMap["userType"] = "user" // Possible values are user/admin, will change value to admin manually on Firebase DB
            hashMap["timeStamp"] = timestamp

            val ref = FirebaseDatabase.getInstance().getReference("Admin")
            ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener {
                    // Handle success, e.g., dismiss progressDialog, navigate to the next activity, etc.
                    progressDialog.dismiss()
                    // You can add further actions here if needed
                    Toast.makeText(this, "Account created..", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivit, HomePage::class.java))
                    finish()
                }
                .addOnFailureListener { e->

                    // Handle failure
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed to create account: ${e.message}", Toast.LENGTH_SHORT).show()

                }
        }
    }

}
