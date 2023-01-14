package com.example.rgr.contract

import androidx.fragment.app.Fragment
import com.example.rgr.Field

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun showRegistrationScreen(fields: MutableList<Field>, recordName: String)

    fun showNextPageScreen()

    fun showSubmitScreen(tempRecord: ArrayList<String>, recordName: String)

    fun showOptionsScreen(fields:  MutableList<Field>, recordName: String)

    fun showUsersScreen(recordName: String)

    fun goBack()

    fun goToMenu()

}