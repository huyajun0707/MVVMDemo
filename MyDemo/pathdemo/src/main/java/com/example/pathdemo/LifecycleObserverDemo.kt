package com.example.kdemo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-09-22 17:41
 * @depiction   ：
 */

class LifecycleObserverDemo : LifecycleObserver{


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner){
        print("onCreat")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory(owner: LifecycleOwner){
        print("onDestory")
    }
}