package com.example.rgr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseFragment <VBinding : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VBinding
) : Fragment() {

    lateinit var binding: VBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater)
        return binding.root
    }

}