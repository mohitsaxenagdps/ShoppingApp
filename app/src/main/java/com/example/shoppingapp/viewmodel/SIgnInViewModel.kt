package com.example.shoppingapp.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.Common.Event
import com.google.firebase.auth.FirebaseAuth

class SIgnInViewModel : ViewModel() {

    val email = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    private val emailErrorMsg = MutableLiveData<Event<String>>()
    val mEmailErrorMsg: LiveData<Event<String>>
        get() = emailErrorMsg
    private val passwordErrorMsg = MutableLiveData<Event<String>>()
    val mPasswordErrorMsg: LiveData<Event<String>>
        get() = passwordErrorMsg
    private val isLoggedIn = MutableLiveData<Event<Boolean>>()
    val mIsLoggedIn: LiveData<Event<Boolean>>
        get() = isLoggedIn
    private val isLoading = MutableLiveData<Event<Boolean>>()
    val mIsLoading: LiveData<Event<Boolean>>
        get() = isLoading
    private val toastMsg = MutableLiveData<Event<String>>()
    val mToastMsg: MutableLiveData<Event<String>>
        get() = toastMsg
    private var firebaseAuth = FirebaseAuth.getInstance()

    init {
        checkUser()
    }

    fun signIn() {
        if (email.value == null) {
            emailErrorMsg.value = Event("Email address is required!")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value.toString().trim()).matches()) {
            emailErrorMsg.value = Event("Invalid email address")
        } else if (password.value == null || password.value.toString().trim().isEmpty()) {
            passwordErrorMsg.value = Event("Password is required!")
        } else {
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        isLoading.value = Event(true)
        firebaseAuth.signInWithEmailAndPassword(email.value!!.trim(), password.value!!.trim())
            .addOnSuccessListener {
                isLoading.value = Event(false)
                toastMsg.value = Event("Logged in successfully")
                isLoggedIn.value = Event(true)
            }
            .addOnFailureListener {
                Log.i("MyTag", it.toString())
                isLoading.value = Event(false)
                toastMsg.value = Event("Login failed: ${it.message}")
                isLoggedIn.value = Event(false)
            }
    }

    private fun checkUser() {
        if (firebaseAuth.currentUser != null) {
            isLoggedIn.value = Event(true)
        }
    }

}