package com.example.filmder.filmder.activities.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.filmder.R
import com.example.filmder.filmder.activities.activities.firebase.FirestoreClass
import com.example.filmder.homePage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BasicActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        var currentUserId = FirestoreClass().getCurrentUserID()
        if (currentUserId.isNotEmpty()) {
            startActivity(Intent(this, homePage::class.java))
        }

            btn_sendsignup.setOnClickListener {
                val intent = Intent(this, signup::class.java)
                startActivity(intent)
                finish()
            }
            btn_login.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
