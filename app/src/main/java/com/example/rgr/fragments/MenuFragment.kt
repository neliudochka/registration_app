package com.example.rgr.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.setFragmentResultListener
import com.example.rgr.*
import com.example.rgr.contract.navigator
import com.example.rgr.databinding.FragmentMenuBinding
import java.io.Serializable

class MenuFragment : BaseFragment<FragmentMenuBinding>(
    FragmentMenuBinding::inflate
) {

    private lateinit var recordName: String
    private lateinit var fields:  MutableList<Field>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setFragmentResultListener(RECORD_NAME_RESULT_KEY) { _, bundle ->
            val result = bundle.getString(KEY_RECORD_NAME) as String
            recordName = result
        }

        setFragmentResultListener(OPT_RESULT_KEY) { _, bundle ->
            val result = bundle.getSerializable(KEY_FIELDS) as MutableList<Field>
            fields = result
            println("new"+fields)

        }

        //відновити стан фрагменту/ініціалізувати
        if(savedInstanceState == null) {
            setDefaultOpt()
        }

        else {
            fields = savedInstanceState.getSerializable(KEY_FIELDS) as MutableList<Field>
            recordName = savedInstanceState.getString(KEY_RECORD_NAME) as String
        }

        super.onCreate(savedInstanceState)
    }

    fun setDefaultOpt() {
        fields = defaultFields
        recordName = "Record.txt"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtonsListeners()
    }

    private fun initButtonsListeners() {
        //Reg Button
        binding.regButton.setOnClickListener {
            val activatedFields = filterFieldsAct(fields)
            println("shih"+activatedFields)
            navigator().showRegistrationScreen(activatedFields, recordName)
        }
        //Options Button
        binding.optButton.setOnClickListener {
            navigator().showOptionsScreen(
                mutableListOf<Field>().apply { addAll(fields) },
            recordName)
        }

        binding.usersButton.setOnClickListener {
            navigator().showUsersScreen(recordName)
        }
    }

    private fun filterFieldsAct(fields:  MutableList<Field>): MutableList<Field> {
        val activatedFields = mutableListOf<Field>()
        fields.forEach{
            if(it.enabled) activatedFields.add(it)
        }
        return activatedFields
    }
    //зберегти стан фрагмента перед знищенням
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_FIELDS, fields as Serializable)
        outState.putString(KEY_RECORD_NAME, recordName)
        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic private val KEY_FIELDS = "KEY_FIELDS"
        @JvmStatic private val OPT_RESULT_KEY = "OPT_RESULT_KEY"


        @JvmStatic private val KEY_RECORD_NAME = "KEY_RECORD_NAME"
        @JvmStatic private val RECORD_NAME_RESULT_KEY = "RECORD_NAME_RESULT_KEY"
    }

}