package com.example.filmder

import java.util.ArrayList

object Constants{

    fun getMovie(): ArrayList<Filmovi_info>{
        val filmovi = ArrayList<Filmovi_info>()

        val film1 = Filmovi_info(1,"Avatar", "Ovde se radi o plavim sranjima", R.drawable.ava)

        filmovi.add(film1)
        return filmovi
    }

}