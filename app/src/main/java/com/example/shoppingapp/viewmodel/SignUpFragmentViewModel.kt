package com.example.shoppingapp.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.Common.Event
import com.google.firebase.auth.FirebaseAuth

class SignUpFragmentViewModel : ViewModel() {

    val name = MutableLiveData<String?>()
    val email = MutableLiveData<String?>()
    val contactNo = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    private val isChecked = MutableLiveData<Boolean>()
    private val nameErrorMsg = MutableLiveData<Event<String>>()
    val mNameErrorMsg: LiveData<Event<String>>
        get() = nameErrorMsg
    private val emailErrorMsg = MutableLiveData<Event<String>>()
    val mEmailErrorMsg: LiveData<Event<String>>
        get() = emailErrorMsg
    private val contactNoErrorMsg = MutableLiveData<Event<String>>()
    val mContactNoErrorMsg: LiveData<Event<String>>
        get() = contactNoErrorMsg
    private val passwordErrorMsg = MutableLiveData<Event<String>>()
    val mPasswordErrorMsg: LiveData<Event<String>>
        get() = passwordErrorMsg
    private val toastMsg = MutableLiveData<Event<String>>()
    val mToastMsg: LiveData<Event<String>>
        get() = toastMsg
    private val isLoading = MutableLiveData<Event<Boolean>>()
    val mIsLoading: LiveData<Event<Boolean>>
        get() = isLoading
    private val isSignedUp = MutableLiveData<Event<Boolean>>()
    val mIsSignedUp: LiveData<Event<Boolean>>
        get() = isSignedUp
    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        isChecked.value = false
    }

    fun signUp() {
        if (name.value == null || name.value.toString().trim().isEmpty()) {
            nameErrorMsg.value = Event("Name is required!")
        } else if (email.value == null || email.value.toString().trim().isEmpty()) {
            emailErrorMsg.value = Event("Email is required!")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value.toString().trim()).matches()) {
            emailErrorMsg.value = Event("Invalid email address")
        } else if (contactNo.value == null || contactNo.value.toString().isEmpty()) {
            contactNoErrorMsg.value = Event("Contact No. is required")
        } else if (!Patterns.PHONE.matcher(contactNo.value.toString().trim()).matches()) {
            contactNoErrorMsg.value = Event("Invalid Contact No.")
        } else if (password.value == null) {
            passwordErrorMsg.value = Event("Password is required!")
        } else if (password.value.toString().trim().length < 6) {
            passwordErrorMsg.value = Event("Password should not be less than 6 characters")
        } else if (isChecked.value == false) {
            toastMsg.value = Event("Please agree terms and conditions")
        } else {
            signingUp()
        }
    }

    private fun signingUp() {
        isLoading.value = Event(true)
        firebaseAuth.createUserWithEmailAndPassword(
            email.value.toString().trim(),
            password.value.toString().trim()
        )
            .addOnSuccessListener {
                isLoading.value = Event(false)
                isSignedUp.value = Event(true)
                toastMsg.value = Event("Account successfully created: Sign in please")
            }
            .addOnFailureListener {
                Log.i("MyTag", it.toString())
                isLoading.value = Event(false)
                toastMsg.value = Event("Sign up failed: ${it.message}")
            }
    }

    fun setChecked(){
        isChecked.value = isChecked.value != true
    }

}