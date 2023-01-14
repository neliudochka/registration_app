package com.example.rgr.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.rgr.Record
import com.example.rgr.contract.navigator
import com.example.rgr.databinding.FragmentSubmitBinding
import java.io.Serializable

class SubmitFragment : BaseFragment<FragmentSubmitBinding>(
    FragmentSubmitBinding::inflate
) {

    private lateinit var tempRecord: ArrayList<String>
    private lateinit var recordName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            tempRecord = it.getSerializable(ARG_TEMP_RECORD) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
            recordName = it.getString(ARG_RECORD_NAME) as String
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecordFrame()
        initPrevButton()
        initSubmitButton()
    }

    private fun initRecordFrame() {
        tempRecord.forEach{
            val newT = TextView(context)
            newT.text = it
            binding.recordFrame.addView(newT)
        }
    }

    private fun initPrevButton() {
        binding.prev.setOnClickListener{
            navigator().goBack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private  fun initSubmitButton() {
        binding.submit.setOnClickListener{
            val file = Record(requireContext(), recordName).getFile()
            tempRecord.forEach {
                file.appendText(it + "\n")
            }
            file.appendText("\n")
            Toast.makeText(context, "Користувача додано", Toast.LENGTH_SHORT).show()
            navigator().goToMenu()
        }
    }

    companion object {
        @JvmStatic private val ARG_RECORD_NAME = "ARG_RECORD_NAME"
        @JvmStatic private val ARG_TEMP_RECORD = "ARG_TEMP_RECORD"

        @JvmStatic
        fun newInstance(tempRecord: ArrayList<String>, recordName: String): SubmitFragment {
            val args = Bundle()
            args.putSerializable(ARG_TEMP_RECORD, tempRecord as Serializable)
            args.putString(ARG_RECORD_NAME, recordName)
            val fragment = SubmitFragment()
            fragment.arguments = args
            return fragment
        }
    }
}