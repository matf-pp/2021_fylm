package com.example.filmder

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_home_page.*
import org.json.JSONArray
import java.io.InputStream
import java.lang.Exception

data class Filmovi_info (
        val id: Int,
        val ime_filma: String,
        val deskripcija: String,
        val image: Int
)


class homePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val likedList= ArrayList<Filmovi_info>()
        val dislikedList= ArrayList<Filmovi_info>()
        //zbog toga sto ovo vraca niz, val filmovi i Constants postaje nebitan
        val niz=citajJson(this)
        //val filmovi = Constants.getMovie() ceo Constants fajl je waste of space za sada
        Log.i("Velicina", "${niz.size}") //debug stuff 4 me
        var brojac=1 //pomocna promenljiva da vidim na kom sam filmu u nizu
        Like.setOnClickListener {
            if(brojac==niz.size) // for rotating purposes, na tinderu kada se potrosi potrosio si
                brojac=0

            iv_image.setImageResource(niz[brojac].image)
            iv_image.maxHeight=200 //MORAO sam da ogranicim height i width jer mi pojede ceo ekran slika, bukv nestanu dugmici
            iv_image.maxWidth=200
           // iv_image.setImageDrawable(resources.getDrawable(niz[brojac].image)) //useless for now ali mozda kasnije
            tv_film.text=niz[brojac].ime_filma
            tv_description.text=niz[brojac].deskripcija

            likedList.add(niz[brojac]) //pamtim lajkovane

            brojac++
        }
        Dislike.setOnClickListener {
            if(brojac==niz.size) //for rotating purposes, u pravom svetu nema ovoga
                brojac=0

            iv_image.setImageResource(niz[brojac].image)
            iv_image.maxHeight=200
            iv_image.maxWidth=200
            tv_film.text=niz[brojac].ime_filma
            tv_description.text=niz[brojac].deskripcija

            dislikedList.add(niz[brojac]) //pamtim dislajkovane

            brojac++
        }

    }

    fun citajJson(context: Context): ArrayList<Filmovi_info>{ // funkcija cita json i vraca niz filmova
        var json: String?=null
        val niz= ArrayList<Filmovi_info>()
        try {
            val input: InputStream =context.assets.open("jsonFilmovi.json") //otvaranje fajla sa podacima

            json=input.bufferedReader().use{it.readText()}

            var jsonarr= JSONArray(json)

            for (i in 0..jsonarr.length()-1) {
                var jsonObj=jsonarr.getJSONObject(i) //zapravo jsonObjekat u kojem se preko polja name pristupa poljima
                //sledece 4 promenljive su polja tj. vrednosti koje prosledjujem konstruktoru
                val id=jsonObj.getInt("id")

                val ime_filma=jsonObj.getString("ime_filma")

                val deskripcija=jsonObj.getString("deskripcija")

                val uri: String= "@drawable/"+jsonObj.getString("slika")

                val imageResource: Int= resources.getIdentifier(uri,null,packageName) //nadam se da je ovo zapravo slika
                val trenutnifilm= Filmovi_info(id,ime_filma, deskripcija,imageResource) //dodajem kompletan film u niz
                niz.add(trenutnifilm)
            }

        }
        catch(e: Exception) {
            e.printStackTrace() //ako dodje do greske u citanju ili necemu drugom
        }
        return niz
    }
}