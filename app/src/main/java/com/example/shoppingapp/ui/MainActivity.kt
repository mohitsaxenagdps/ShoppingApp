package com.example.shoppingapp.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        lateinit var binding: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(setOf(R.id.signInFragment, R.id.signUpFragment))

        val shape = GradientDrawable()
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 60f, 60f, 60f, 60f)
        shape.setColor(Color.parseColor("#FFFFFF"))
        val shape1 = GradientDrawable()
        shape1.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 60f, 60f, 60f, 60f)
        shape1.setColor(Color.parseColor("#C12C2C"))
        binding.apply {
            btSignIn.background = shape1
            btSignIn.setTextColor(Color.parseColor("#FFFFFF"))
            btSignUp.background = shape
            btSignUp.setTextColor(Color.parseColor("#000000"))
        }

        binding.apply {
            btSignIn.setOnClickListener {
                if (navController.currentDestination?.id != R.id.signInFragment) {
                    navController.navigate(R.id.action_signUpFragment_to_signInFragment)
                    btSignIn.background = shape1
                    btSignIn.setTextColor(Color.parseColor("#FFFFFF"))
                    btSignUp.background = shape
                    btSignUp.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

        binding.apply {
            btSignUp.setOnClickListener {
                if (navController.currentDestination?.id != R.id.signUpFragment) {
                    navController.navigate(R.id.action_signInFragment_to_signUpFragment)
                    btSignUp.background = shape1
                    btSignUp.setTextColor(Color.parseColor("#FFFFFF"))
                    btSignIn.background = shape
                    btSignIn.setTextColor(Color.parseColor("#000000"))
                }
            }
        }

    }

}