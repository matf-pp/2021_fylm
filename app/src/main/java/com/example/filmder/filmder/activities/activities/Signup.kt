package com.example.filmder.filmder.activities.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.example.filmder.R
import com.example.filmder.filmder.activities.activities.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup.et_username_signup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_sendsignup
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.delay
import modules.User


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
            val mAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser:FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(firebaseUser.uid,name,registeredEmail,"","",ArrayList())
                    FirestoreClass().registerUser(this,user)
                } else {
                    ErrorSnackBarShow(task.exception!!.message.toString())
                }
            }

        }
    }

    fun userRegisteredSuccess(){
        hideProgressDialog()
        ErrorSnackBarShow("you have succesfully registered the email adress")
        val intent = Intent(this, friends::class.java)
        startActivity(intent)
        finish()
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

