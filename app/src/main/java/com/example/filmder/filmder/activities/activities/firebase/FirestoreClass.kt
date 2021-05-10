package com.example.filmder.filmder.activities.activities.firebase

import android.util.Log
import com.example.filmder.filmder.activities.activities.Login
import com.example.filmder.filmder.activities.activities.friends
import com.example.filmder.filmder.activities.activities.signup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import modules.User

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: signup, userInfo: User) {
        mFireStore.collection("Users")
            .document(getCurrentUserID()).set(userInfo, SetOptions.merge()).addOnSuccessListener {
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

    fun getBoardsList(activity: friends) {

        // The collection name for BOARDS
        mFireStore.collection("Users")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // Here we get the list of boards in the form of documents.
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                // Here we have created a new instance for Boards ArrayList.
                val boardsList: ArrayList<User> = ArrayList()

                // A for loop as per the list of documents to convert them into Boards ArrayList.
                for (i in document.documents) {

                    val board = i.toObject(User::class.java)!!
                    boardsList.add(board)
                }

                // Here pass the result to the base activity.
                activity.populateBoardsListToUI(activity,boardsList)
            }
            .addOnFailureListener { e ->


                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
            }
    }
}
