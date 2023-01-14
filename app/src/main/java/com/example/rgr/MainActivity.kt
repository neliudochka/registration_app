package com.example.rgr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.rgr.contract.*
import com.example.rgr.databinding.ActivityMainBinding
import com.example.rgr.fragments.*

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, MenuFragment())
                .commit()
        }

    }

    override fun showRegistrationScreen(fields: MutableList<Field>, recordName: String) {
        launchFragment(PageFragment.newInstance(fields, recordName))
    }

    override fun showNextPageScreen() {
        launchFragment(PageFragment.nextPage())
    }

    override fun showSubmitScreen(tempRecord: ArrayList<String>, recordName: String) {
        launchFragment(SubmitFragment.newInstance(tempRecord, recordName))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun showOptionsScreen(fields: MutableList<Field>, recordName: String) {
        launchFragment(OptionsFragment.newInstance(fields, recordName))
    }

    override fun showUsersScreen(recordName: String) {
        launchFragment(UsersFragment.newInstance(recordName))
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }


    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        @JvmStatic private val KEY_RESULT = "RESULT"
    }

}