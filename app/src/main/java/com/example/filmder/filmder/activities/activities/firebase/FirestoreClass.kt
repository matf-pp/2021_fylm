package com.example.filmder.filmder.activities.activities.firebase

import android.util.Log
import com.example.filmder.filmder.activities.activities.Login
import com.example.filmder.filmder.activities.activities.signup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import modules.User

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity:signup,userInfo: User) {
        mFireStore.collection("Users")
                .document(getCurrentUserID()).set(userInfo, SetOptions.merge()).addOnSuccessListener {
                    activity.userRegisteredSuccess()
                }.addOnFailureListener { Log.e(activity.javaClass.simpleName, "Error writing document") }
    }

    fun signInUser(activity: Login){
        mFireStore.collection("Users")
                .document(getCurrentUserID()).get()
                .addOnSuccessListener { document ->
                    val loggedInUser: User = document.toObject(User::class.java)!!
                    activity.signInSuccess(loggedInUser)

                }
    }

    fun getCurrentUserID():String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}