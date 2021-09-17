package com.example.shoppingapp.ui

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
import com.example.shoppingapp.viewmodel.SignUpFragmentViewModel
import com.example.shoppingapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        viewModel = ViewModelProvider(this)[SignUpFragmentViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        val customProgressDialog = CustomProgressDialog(requireContext())

        val shape = GradientDrawable()
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 60f, 60f, 60f, 60f)
        shape.setColor(Color.parseColor("#FFFFFF"))
        val shape1 = GradientDrawable()
        shape1.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 60f, 60f, 60f, 60f)
        shape1.setColor(Color.parseColor("#C12C2C"))

        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            MainActivity.binding.apply {
                btSignIn.background = shape1
                btSignIn.setTextColor(Color.parseColor("#FFFFFF"))
                btSignUp.background = shape
                btSignUp.setTextColor(Color.parseColor("#000000"))
            }
        }

        viewModel.mNameErrorMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg->
                binding.etName.error = msg
            }
        })

        viewModel.mEmailErrorMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg->
                binding.etEmail.error = msg
            }
        })

        viewModel.mContactNoErrorMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg->
                binding.etContactNo.error = msg
            }
        })

        viewModel.mPasswordErrorMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg->
                binding.etPassword.error = msg
            }
        })

        viewModel.mIsLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { value->
                if(value){
                    customProgressDialog.showDialog("Please Wait")
                } else {
                    customProgressDialog.dismissDialog()
                }
            }
        })

        viewModel.mToastMsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { msg->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.mIsSignedUp.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { value->
                if(value){
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                    MainActivity.binding.appBar.visibility = View.GONE
                    MainActivity.binding.ll2.visibility = View.GONE
                }
            }
        })

        return binding.root

    }

}