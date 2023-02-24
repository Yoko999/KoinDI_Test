package com.example.dikointest

public class MyFactoryForFragmentClass constructor(val frName:String) {
    fun printFragmentName(){
        println("My fragment is $frName, my instance name is $this")
    }
}