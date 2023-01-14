package com.example.rgr.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.rgr.Field
import com.example.rgr.contract.navigator
import com.example.rgr.databinding.FragmentPageBinding
import java.io.Serializable

class PageFragment : BaseFragment<FragmentPageBinding>(
    FragmentPageBinding::inflate)
{
    private lateinit var field: Field
    private lateinit var fieldView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            fields = it.getSerializable(ARG_FIELDS) as MutableList<Field>
            recordName = it.getString(ARG_RECORD_NAME) as String
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initField()
        initPrevButton()
        initNextButton()
    }

    //ініціалізувати поле
    private fun initField() {
        field = fields[index]
        val newT = EditText(context)
        newT.hint = field.hint
        fieldView = newT
        binding.fieldFrame.addView(newT)
        readTempRecord()
    }

    //ініціалізація кнопок
    private fun initNextButton() {
        binding.next.setOnClickListener{
            if(checkFieldInput()) {
                makeTempRecord()
                if(index < fields.size-1) {
                    index++
                    navigator().showNextPageScreen()
                 } else {
                     index = 0
                  navigator().showSubmitScreen(temporaryRecord, recordName)
                }
            }
        }
    }

    private fun initPrevButton() {
        binding.prev.setOnClickListener{
            if(index != 0) index--
            navigator().goBack()
        }
    }

    private fun makeTempRecord() {
        val record = field.name + ": " +
                fieldView.text.toString()
        temporaryRecord.set(index, record)
    }

    private fun readTempRecord() {
        if(temporaryRecord[index] != "") {
            val text = temporaryRecord[index].split(" ")[1]
            fieldView.setText(text)
        }
    }

    private fun checkFieldInput(): Boolean {
        if(fieldView.text.toString() == ""){
            fieldView.error = "Порожнє поле"
            return false
        }
        if(!fieldView.text.toString().contains(field.inputRestriction)){
            fieldView.error = "Поле має містити " + field.inputRestriction
            return false
        }
        return true
    }
    companion object {
        @JvmStatic private val ARG_FIELDS = "ARG_OPTIONS"
        @JvmStatic private val ARG_RECORD_NAME = "ARG_RECORD_NAME"
        @JvmStatic private lateinit var fields:  MutableList<Field>
        @JvmStatic private var index = 0
        @JvmStatic private lateinit var temporaryRecord: ArrayList<String>
        @JvmStatic private lateinit var recordName: String

        @JvmStatic
        fun newInstance(fields: MutableList<Field>, recordName: String): PageFragment {
            //init tempRecord
            val arrayOfEmptyStrings = ArrayList<String>()
            for (i in 0 until fields.size) {
                arrayOfEmptyStrings.add("")
            }
            temporaryRecord = arrayOfEmptyStrings
            //init record
            val args = Bundle()
            args.putSerializable(ARG_FIELDS, fields as Serializable)
            args.putString(ARG_RECORD_NAME, recordName)
            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun nextPage(): PageFragment{
            return PageFragment()
        }
    }
}