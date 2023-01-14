package com.example.rgr.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.rgr.Record
import com.example.rgr.databinding.FragmentUsersBinding

class UsersFragment : BaseFragment<FragmentUsersBinding>(
    FragmentUsersBinding::inflate
) {
    private lateinit var recordName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            recordName = it.getString(ARG_RECORD_NAME) as String
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecord()
        initDeletRecord()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecord() {
        Record(requireContext(), recordName)
        val file = Record(requireContext(), recordName).getFile()
        if(file.exists()) {
            binding.recordFrame.removeAllViews()
            val newT = TextView(context)
            newT.text = file.bufferedReader().readText()
            binding.recordFrame.addView(newT)
        } else {
            binding.recordFrame.removeAllViews()
            val newT = TextView(context)
            newT.text = "Жоден користувач ще не був зареєстрований"
            binding.recordFrame.addView(newT)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDeletRecord() {
        binding.deleteRecord.setOnClickListener{
            Record(requireContext(), recordName).deleteFile()
            initRecord()
        }
    }


    companion object {
        @JvmStatic private val ARG_RECORD_NAME = "ARG_RECORD_NAME"

        @JvmStatic
        fun newInstance(recordName: String): UsersFragment {
            val args = Bundle()
            args.putString(ARG_RECORD_NAME, recordName)
            val fragment = UsersFragment()
            fragment.arguments = args
            return fragment
        }
    }
}