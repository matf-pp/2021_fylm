package com.example.filmder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btn_sendsignup.setOnClickListener {
                val intent = Intent(this, signup::class.java)
                startActivity(intent)
                finish()
            }
        btn_login.setOnClickListener{
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }
        }


    }
