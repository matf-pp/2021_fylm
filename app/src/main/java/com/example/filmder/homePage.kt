package com.example.filmder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_home_page.*

class homePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val filmovi = Constants.getMovie()
        Log.i("Velicina", "${filmovi.size}")

        val pozicija = 1;
        val film : Filmovi_info? = filmovi[pozicija - 1]

        //iv_image.setImageResource(filmovi.image)

    }
}