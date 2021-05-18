package com.example.filmder.filmder.activities.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import com.example.filmder.R
import com.example.filmder.filmder.activities.activities.firebase.FirestoreClass
import com.example.filmder.homePage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friends.*
import modules.User

class friends : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        showProgressDialog("Please Wait we are loading friends")
        val mFireStore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser.uid
        mFireStore.collection("Users")
            .get()
            .addOnSuccessListener { document ->
                val userList: ArrayList<User> = ArrayList()
                if (document!=null){
                    hideProgressDialog()
                    for (i in document.documents) {

                    val user = i.toObject(User::class.java)!!
                        if(user.id!=currentUser)
                            userList.add(user)
                }

                // Here pass the result to the base activity.
                this.populateBoardsListToUI(this, userList)
                }
            }
            .addOnFailureListener { e ->
                hideProgressDialog()
                ErrorSnackBarShow("Couldnt show friends" + {e.printStackTrace()})
                Log.e(this.javaClass.simpleName, "Error while creating a board.", e)
            }
    }


    fun populateBoardsListToUI(activity: friends, userList: ArrayList<User>) {
        //Log.i("Velicina", "bla15")
        val listview= findViewById<ListView>(R.id.prijateljipozadina)
        val niz: ArrayList<String> = ArrayList()
        for(user in userList) {
            user.name?.let { niz.add(it) }
        }
        val nizAdapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1, niz)
        listview.adapter=nizAdapter
        listview.setOnItemClickListener { parent, view, position, id ->
            //ErrorSnackBarShow(niz[id.toInt()])
            var intent = Intent(this,homePage::class.java)
            intent.putExtra("id",userList[position].id)
            startActivity(intent)
        }

    }

}

