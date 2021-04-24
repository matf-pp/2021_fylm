package com.example.filmder.filmder.activities.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.filmder.R
import com.example.filmder.homePage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_username_signup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_sendsignup
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.delay
import kotlinx.android.synthetic.main.activity_login.et_password_signup as et_password_signup1

class signup : BasicActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_signup)
        btn_sendsignup.setOnClickListener {
                registerUser()
        }
    }

    private fun registerUser() {
        val name: String = et_username_signup.text.toString().trim() { it <= ' ' }
        val email: String = et_email_signup.text.toString().trim() { it <= ' ' }
        val password: String = et_password_signup.text.toString().trim() { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    { task ->
                        hideProgressDialog()
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val registeredEmail = firebaseUser.email!!
                            ErrorSnackBarShow("you have succesfully registered the email adress $email")
                            val intent = Intent(this, homePage::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })

        }
    }


    private fun validateForm(name:String,email:String,password:String) : Boolean{
        return when {
            TextUtils.isEmpty(name) -> {
                ErrorSnackBarShow("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                ErrorSnackBarShow("Please enter an email adress")
                false
            }
            TextUtils.isEmpty(password) -> {
                ErrorSnackBarShow("Please enter a password")
                false
            }
            else -> true
        }
    }
}

