package com.example.shoppingapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.Common.CustomProgressDialog
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentSignInBinding
import com.example.shoppingapp.viewmodel.SIgnInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SIgnInViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val reqCode = 123
    private var firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        viewModel = ViewModelProvider(this)[SIgnInViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        val customProgressBar = CustomProgressDialog(requireContext())
        val shape = GradientDrawable()
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 60f, 60f, 60f, 60f)
        shape.setColor(Color.parseColor("#FFFFFF"))
        val shape1 = GradientDrawable()
        shape1.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 60f, 60f, 60f, 60f)
        shape1.setColor(Color.parseColor("#C12C2C"))

        binding.tvRegisterNow.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            MainActivity.binding.apply {
                btSignUp.background = shape1
                btSignUp.setTextColor(Color.parseColor("#FFFFFF"))
                btSignIn.background = shape
                btSignIn.setTextColor(Color.parseColor("#000000"))
            }
        }

        viewModel.mEmailErrorMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg ->
                binding.etEmail.error = msg
            }
        })

        viewModel.mPasswordErrorMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg ->
                binding.etPassword.error = msg
            }
        })

        viewModel.mIsLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { value ->
                if (value) {
                    customProgressBar.showDialog("Please Wait")
                } else {
                    customProgressBar.dismissDialog()
                }
            }
        })

        viewModel.mToastMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.mIsLoggedIn.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { value ->
                if (value) {
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    MainActivity.binding.appBar.visibility = View.GONE
                    MainActivity.binding.ll2.visibility = View.GONE
                }
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(binding.root.context as Activity, gso)

        binding.btGoogle.setOnClickListener {
            signInGoogle()
        }

        return binding.root
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, reqCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                MainActivity.binding.appBar.visibility = View.GONE
                MainActivity.binding.ll2.visibility = View.GONE
            }
        }
    }

}