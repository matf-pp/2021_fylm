package com.example.filmder.filmder.activities.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.example.filmder.R
import com.example.filmder.filmder.activities.activities.firebase.FirestoreClass
import com.example.filmder.homePage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import modules.User

class Login : BasicActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_sendlogin.setOnClickListener {
            signInRegisteredUser()
        }
    }

    private fun signInRegisteredUser() {
        val email: String = et_email_login.text.toString().trim {it<=' '}
        val password:String = et_password_login.text.toString().trim(){it<=' '}
        if(validateForm(email,password)){
            //signin the user
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    ErrorSnackBarShow("Sign in with $email Success")
                    FirestoreClass().signInUser(this)

                }else{
                    ErrorSnackBarShow(task.exception.toString())
                }
            }
        }else{
            //signin error
            ErrorSnackBarShow("You have to enter your username and password")
        }
    }

    fun signInSuccess(user:User){
        hideProgressDialog()
        val intent = Intent(this, friends::class.java)
        startActivity(intent)
        finish()
    }


    private fun validateForm(email:String,password:String) : Boolean{
        return when {
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