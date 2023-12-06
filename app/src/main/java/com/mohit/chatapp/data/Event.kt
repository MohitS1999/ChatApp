package com.mohit.chatapp.data

open class Event<out T>(private val content:T) {
    private var hasBeenHanded = false
    fun getContentOrNull():T?{
        return if(hasBeenHanded) null
        else{
            hasBeenHanded = true
            content
        }
    }
}