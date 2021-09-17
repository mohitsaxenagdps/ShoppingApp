package com.example.shoppingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.Common.Event
import com.google.firebase.auth.FirebaseAuth

class HomeFragmentViewModel : ViewModel() {

    private val isLoggedOut = MutableLiveData<Event<Boolean>>()
    val name = MutableLiveData<String>()
    val mIsLoggedOut: LiveData<Event<Boolean>>
        get() = isLoggedOut
    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        name.value = firebaseAuth.currentUser?.email
    }

    fun logOut() {
        firebaseAuth.signOut()
        isLoggedOut.value = Event(true)
    }

}