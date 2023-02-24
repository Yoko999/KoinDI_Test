package com.example.dikointest

import android.content.Context

public class SomeDB(private val appcontext:Context) {
    fun doSomeDbThing(){
        println("DB with appContext: ${appcontext.toString()}")
    }
}