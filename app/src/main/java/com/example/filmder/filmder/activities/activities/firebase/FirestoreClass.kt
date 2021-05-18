package com.example.filmder.filmder.activities.activities.firebase

import android.util.Log
import com.example.filmder.filmder.activities.activities.Login
import com.example.filmder.filmder.activities.activities.friends
import com.example.filmder.filmder.activities.activities.signup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import modules.User

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()
    private val mReal = FirebaseDatabase.getInstance()

    fun registerUser(activity: signup, userInfo: User) {
        val thisUser = mFireStore.collection("Users").document(getCurrentUserID())
        thisUser.set(userInfo, SetOptions.merge()).addOnSuccessListener {
                //var ArrayofId = ArrayList<Int>()
                //thisUser.update("movieId",ArrayofId)
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { Log.e(activity.javaClass.simpleName, "Error writing document") }

    }

    fun signInUser(activity: Login) {
        mFireStore.collection("Users")
            .document(getCurrentUserID()).get()
            .addOnSuccessListener { document ->
                val loggedInUser: User = document.toObject(User::class.java)!!
                activity.signInSuccess(loggedInUser)

            }
    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }




   /* fun getUserFromUsername(username:String){
        val connectedUser = mFireStore.collection("Users")
                .whereEqualTo("name",username)
        connectedUser.addSnapshotListener{snapshot,e ->
            if(e!=null)
        }

    }*/
    }

