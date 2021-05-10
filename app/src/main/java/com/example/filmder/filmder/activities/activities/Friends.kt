package com.example.filmder.filmder.activities.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.filmder.R
import com.example.filmder.filmder.activities.activities.firebase.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friends.*
import modules.User

class friends : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        var mFireStore = FirebaseFirestore.getInstance()
        mFireStore.collection("Users")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // Here we get the list of boards in the form of documents.
                Log.e(this.javaClass.simpleName, document.documents.toString())
                // Here we have created a new instance for Boards ArrayList.
                val boardsList: ArrayList<User> = ArrayList()

                // A for loop as per the list of documents to convert them into Boards ArrayList.
                for (i in document.documents) {

                    val board = i.toObject(User::class.java)!!
                    boardsList.add(board)
                }

                // Here pass the result to the base activity.
                this.populateBoardsListToUI(this, boardsList)
            }
            .addOnFailureListener { e ->


                Log.e(this.javaClass.simpleName, "Error while creating a board.", e)
            }
    }

    fun populateBoardsListToUI(activity: friends, userList: ArrayList<User>) {
        //Log.i("Velicina", "bla15")
        val listview= findViewById<ListView>(R.id.prijateljipozadina)
        val niz: ArrayList<String> = ArrayList()
        for(user in userList) {
            niz.add(user.name)
        }
        val nizAdapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1, niz)
        listview.adapter=nizAdapter
        //listview.setOnItemClickListener() TODO : povezivanje sa korisnikom na klik

    }

}

