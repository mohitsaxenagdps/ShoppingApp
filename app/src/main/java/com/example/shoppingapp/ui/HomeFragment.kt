package com.example.shoppingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.SliderAdapter
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.viewmodel.HomeFragmentViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    activity?.finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            activity, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        val sliderAdapter = SliderAdapter()
        binding.slider.apply {
            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            setSliderAdapter(sliderAdapter)
            scrollTimeInSec = 2
            isAutoCycle = true
            startAutoCycle()
        }

        viewModel.mIsLoggedOut.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { value ->
                if (value) {
                    findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                    MainActivity.binding.appBar.visibility = View.VISIBLE
                    MainActivity.binding.ll2.visibility = View.VISIBLE
                }
            }
        })

        return binding.root

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                MainActivity.binding.appBar.visibility = View.VISIBLE
                MainActivity.binding.ll2.visibility = View.VISIBLE
            }
        }
        return true
    }

}