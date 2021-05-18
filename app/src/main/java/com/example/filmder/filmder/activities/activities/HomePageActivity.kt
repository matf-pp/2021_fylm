package com.example.filmder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import com.example.filmder.filmder.activities.activities.BasicActivity
import modules.Filmovi_info
import com.example.filmder.filmder.activities.activities.MainActivity
import com.example.filmder.filmder.activities.activities.friends
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_home_page.*
import modules.User
import org.json.JSONArray
import java.io.InputStream
import kotlin.reflect.typeOf


class homePage : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val db = FirebaseFirestore.getInstance().collection("Users")
        val ThisUser = db.document(FirebaseAuth.getInstance().uid.toString())
        val connectedUsername = intent.getStringExtra("id")
        val otherUser = db.document(connectedUsername!!)
        var OtherUserClass:User//zbog toga sto ovo vraca niz, val filmovi i Constants postaje nebitan
        var OtherUserMovies:ArrayList<Long> = ArrayList()
        val MovieList = citajJson(this)
        var counter = 0 //pomocna promenljiva da vidim na kom sam filmu u nizu

        DrawMovies(MovieList,counter)
        otherUser.get().addOnSuccessListener { document ->
            if(document!=null){
                OtherUserClass=document.toObject(User::class.java)!!
                OtherUserMovies=OtherUserClass.moviesId
            }
        }
        Like.setOnClickListener {
            if (counter == MovieList.size-1) // for rotating purposes, na tinderu kada se potrosi potrosio si
                counter = 0
            DrawMovies(MovieList,counter+1)
            ThisUser.update("moviesId", FieldValue.arrayUnion(MovieList[counter].id))
            otherUser.addSnapshotListener { value, error ->
                if (value != null) {
                    OtherUserClass = value.toObject(User::class.java)!!
                    OtherUserMovies=OtherUserClass.moviesId
                }
            }


            if(OtherUserMovies.contains(MovieList[counter].id.toLong())){
                DrawMatch(MovieList[counter])
                ThisUser.update("moviesId", ArrayList<Long>())
                otherUser.update("moviesId",ArrayList<Long>())
                }
                counter++

            }
            Dislike.setOnClickListener {
                if (counter == MovieList.size-1) { //for rotating purposes, u pravom svetu nema ovoga
                    counter = 0
                }
                DrawMovies(MovieList,counter+1)
                ThisUser.update("moviesId", FieldValue.arrayRemove(MovieList[counter].id))
                counter++
            }
            pop_up.setOnClickListener {
                popout(ThisUser)
            }
        }



    fun DrawMovies(niz:ArrayList<Filmovi_info>,position:Int){
        iv_image.setImageResource(niz[position].image)
        iv_image.maxHeight = 200
        iv_image.maxWidth = 200
        tv_film.text = niz[position].ime_filma
        tv_description.text = niz[position].deskripcija
    }

    fun DrawMatch(winner:Filmovi_info){
        matchimage.setImageResource(winner.image)
        matchTitle.text=winner.ime_filma
        matchdescription.text=winner.deskripcija
        cestitka.visibility=View.VISIBLE
    }




    fun citajJson(context: Context): ArrayList<Filmovi_info>{ // funkcija cita json i vraca niz filmova
        var json: String?=null
        val niz= ArrayList<Filmovi_info>()
        try {
            val input: InputStream =context.assets.open("jsonFilmovi.json") //otvaranje fajla sa podacima

            json=input.bufferedReader().use{it.readText()}

            val jsonarr= JSONArray(json)

            for (i in 0..jsonarr.length()-1) {
                val jsonObj=jsonarr.getJSONObject(i) //zapravo jsonObjekat u kojem se preko polja name pristupa poljima
                //sledece 4 promenljive su polja tj. vrednosti koje prosledjujem konstruktoru
                val id=jsonObj.getInt("id")

                val ime_filma=jsonObj.getString("ime_filma")

                val deskripcija=jsonObj.getString("deskripcija")

                val uri: String= "@drawable/"+jsonObj.getString("slika")

                val imageResource: Int= resources.getIdentifier(uri, null, packageName) //nadam se da je ovo zapravo slika
                val trenutnifilm= Filmovi_info(id, ime_filma, deskripcija, imageResource) //dodajem kompletan film u niz
                niz.add(trenutnifilm)
            }

        }
        catch (e: Exception) {
            e.printStackTrace() //ako dodje do greske u citanju ili necemu drugom
        }
        return niz
    }

    private fun popout(ThisUser:DocumentReference){

        val popupmenu = PopupMenu(applicationContext, pop_up)
        popupmenu.inflate(R.menu.popup_menu)
        popupmenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.friends -> {
                    prijatelji(ThisUser)
                    true
                }
                R.id.log_out -> {
                    logout(ThisUser)
                    true
                }
                else -> true
            }
        }
        pop_up.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupmenu)
                menu.javaClass
                        .getDeclaredMethod("setForcedShowIcon", Boolean::class.java)
                        .invoke(menu, true)
            }catch (e: Exception) {
                e.printStackTrace()
            }finally {
                popupmenu.show()
            }
                true
            }
        }

        fun prijatelji(ThisUser:DocumentReference) {
            ThisUser.update("moviesId", ArrayList<Long>())
            val intent = Intent(this, friends::class.java)
            startActivity(intent)
        }
        fun logout(ThisUser:DocumentReference) {
            ThisUser.update("moviesId", ArrayList<Long>())
            val intent = Intent(this, MainActivity::class.java)
            FirebaseAuth.getInstance().signOut()
            ErrorSnackBarShow("Signout success")
            startActivity(intent)
        }
}



