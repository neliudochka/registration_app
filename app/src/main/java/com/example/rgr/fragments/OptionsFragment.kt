package com.example.rgr.fragments

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import com.example.rgr.Field
import com.example.rgr.R
import com.example.rgr.contract.navigator
import com.example.rgr.databinding.FragmentOptionsBinding
import java.io.Serializable

class OptionsFragment : BaseFragment<FragmentOptionsBinding>(
    FragmentOptionsBinding::inflate
) {

    private lateinit var fields:  MutableList<Field>
    private lateinit var recordName: String

    private var fieldViews = mutableListOf<CheckBox>()
    private lateinit var recordNameView: EditText

    private lateinit var newField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            fields = it.getSerializable(ARG_FIELDS) as MutableList<Field>
            recordName = it.getString(ARG_RECORD_NAME) as String
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToDefaultOptButton()
        reloadView()
    }

    private fun initToDefaultOptButton() {
        binding.toDefault.setOnClickListener {
            fields = mutableListOf<Field>().apply { addAll(defaultFields) }
            recordName = "" + defaultRecordName

            println("IOI"+fields+recordName)
            reloadView()
        }
    }

    private fun reloadView() {
        binding.fieldsFrame.removeAllViews()
        fieldViews.clear()
        initRecordName()
        initApplyNameButton()
        initCheckBoxList()
        initAddFieldButton()
        initApplyFieldsButton()
    }
    private fun initRecordName() {
        val newT = TextView(context)
        newT.text = "Назва файлу для запису:"
        val newET = EditText(context)
        newET.setText(recordName.split(".")[0])
        recordNameView = newET
        binding.fieldsFrame.addView(newT)
        binding.fieldsFrame.addView(newET)
    }

    private fun initApplyNameButton() {
        val applyNameButton = Button(context)
        applyNameButton.text = "Застосувати"
        applyNameButton.setOnClickListener{applyNameButtonListener()}
        binding.fieldsFrame.addView(applyNameButton)
    }


    private fun applyNameButtonListener() {
        recordName = recordNameView.text.toString()
        Toast.makeText(context, recordName, Toast.LENGTH_SHORT).show()

        val result = Bundle()
        result.putString(KEY_RECORD_NAME, recordName+".txt")
        setFragmentResult(RECORD_NAME_RESULT_KEY, result)

        Toast.makeText(context, "Назву файлу для результатів змінено", Toast.LENGTH_SHORT).show()
    }

    private fun initCheckBoxList() {
        fields.forEach{
            val linLay = LinearLayout(requireContext())
            linLay.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val newCheckBox = initCheckBox(it)
            linLay.addView(newCheckBox)

            val newDelView = initDelButton(it)
            linLay.addView(newDelView)

            binding.fieldsFrame.addView(linLay)

        }
        println(fieldViews)
    }

    private fun initCheckBox(field: Field): CheckBox {
        val fieldView = CheckBox(context)
        fieldView.text = field.name
        if(field.enabled) fieldView.isChecked = true
        fieldView.setOnClickListener{fieldListener(field, fieldView)}
        fieldViews.add(fieldView)
        return fieldView
    }

    private fun fieldListener(field: Field, fieldView: CheckBox) {
        if(field.required) {
            fieldView.isChecked = true
            Toast.makeText(context, "Обов'язкове поле", Toast.LENGTH_SHORT).show()
        }
        println("pup")
        fieldViews.forEach{ println(it.isChecked)}
        saveState()
    }

    private fun initDelButton(field: Field): TextView {
        val delView = TextView(context)
        delView.text = "    x"
        delView.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.teal_200))

        delView.setOnClickListener{
            delView.setOnClickListener{delListener(field)}
        }
        return delView
    }

    private fun delListener(field: Field) {
        if(field.required) {
            Toast.makeText(context, "Обов'язкове поле -> неможна видалити", Toast.LENGTH_SHORT).show()
        } else {
            saveState()
            fields.remove(field)
            println("millok"+fields)
            reloadView()
        }
    }

    private fun initAddFieldButton() {
        val linLay = LinearLayout(context)
        linLay.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val newCheckBox = CheckBox(context)
        linLay.addView(newCheckBox)

        newField = EditText(context)
        newField.hint = "+"
        linLay.addView(newField)

        newCheckBox.setOnClickListener{
            if(newField.text.toString() == ""){
                newCheckBox.isChecked = false
                Toast.makeText(context, "Нове поле має бути заповненим", Toast.LENGTH_SHORT).show()
            } else {
                saveState()
                fields.add(Field(
                    newField.text.toString(),
                    "Введіть " + newField.text.toString(),
                    "String",
                    "",
                    false,
                    true
                ))
                reloadView()
            }
        }

        binding.fieldsFrame.addView(linLay)
    }

    private fun initApplyFieldsButton() {
        val applyFieldsButton = Button(context)
        applyFieldsButton.text = "Застосувати"
        applyFieldsButton.setOnClickListener{applyButtonListener()}
        binding.fieldsFrame.addView(applyFieldsButton)
    }

    private fun applyButtonListener() {
        Toast.makeText(context, "Опції оновлено", Toast.LENGTH_SHORT).show()
        //передаємо інформацію назад
        saveState()

        val result = Bundle()
        println(fields)
        result.putSerializable(KEY_FIELDS, fields as Serializable)
        setFragmentResult(OPT_RESULT_KEY, result)
    }

    private fun saveState() {
        println("po")
        fieldViews.forEach{ println(it.isChecked)}
        println("p")
        for (i in 0 until fieldViews.size) {
            println(fieldViews[i].isChecked)
            fields[i].enabled = fieldViews[i].isChecked

                println("mils"+i+fields[i].enabled)
        }
        recordName = recordNameView.text.toString()
    }

    companion object {
        @JvmStatic private val ARG_FIELDS = "ARG_OPTIONS"
        @JvmStatic private val OPT_RESULT_KEY = "OPT_RESULT_KEY"
        @JvmStatic private val KEY_FIELDS = "KEY_FIELDS"


        @JvmStatic private val KEY_RECORD_NAME = "KEY_RECORD_NAME"
        @JvmStatic private val ARG_RECORD_NAME = "ARG_RECORD_NAME"
        @JvmStatic private val RECORD_NAME_RESULT_KEY = "RECORD_NAME_RESULT_KEY"


        @JvmStatic private lateinit var defaultFields:  MutableList<Field>
        @JvmStatic private lateinit var defaultRecordName: String

        @JvmStatic
        fun newInstance(fields: MutableList<Field>, recordName: String): OptionsFragment {
            defaultFields = mutableListOf<Field>().apply { addAll(fields) }
            defaultRecordName = "" + recordName
            val args = Bundle()
            args.putSerializable(ARG_FIELDS, fields as Serializable)
            args.putString(ARG_RECORD_NAME, recordName)
            val fragment = OptionsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}