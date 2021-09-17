package com.example.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.SliderLayoutBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter : SliderViewAdapter<SliderAdapter.ViewHolder>() {

    private val sliderList = arrayListOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)

    override fun getCount() = sliderList.size

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val binding = SliderLayoutBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        viewHolder?.bind(sliderList[position])
    }

    inner class ViewHolder(private val binding: SliderLayoutBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(image: Int) {
            Glide.with(binding.root.context)
                .load(image)
                .into(binding.imageView)
        }
    }

}