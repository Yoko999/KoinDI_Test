package com.example.dikointest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

public class MyViewModel constructor(val db:SomeDB, val someParam:Int, val str:String?):ViewModel() {

    public fun doSomeThing(){
        println("SomeParam = $someParam")
        println("Str param = $str")
        db.doSomeDbThing()
        println("ViewModel instance class $this")
    }

    //??? надо или нет?
   /* public class MyVMFactory(val db:SomeDB, val someParam:Int, val str: String?): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyViewModel(db, someParam,str) as T
        }
    }*/
}