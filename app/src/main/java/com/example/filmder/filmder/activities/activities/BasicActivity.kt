package com.example.filmder.filmder.activities.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.filmder.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_progress.*

open class BasicActivity : AppCompatActivity() {

    private var doubleBacktoExitPressedOnce = false

    private lateinit var mProgressDialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
    }

    fun showProgressDialog(text:String){
        mProgressDialog = Dialog(this)

        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun getCurrentUserId() : String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    protected fun ErrorSnackBarShow(message:String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.snackbar_error_color))
        snackBar.show()
    }
}