package com.example.myluaapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myluaapplication.ui.fragment.SuccessFragment
import com.example.myluaapplication.ui.fragment.FragmentA
import com.example.myluaapplication.ui.fragment.FragmentB
import com.example.myluaapplication.ui.GuiAware
import com.example.myluaapplication.ui.fragment.HomeFragment
import com.example.myluaapplication.lua.libs.GuiContract
import com.example.myluaapplication.lua.libs.GuiLib
import com.example.myluaapplication.lua.libs.NavigationLib
import com.example.myluaapplication.lua.LuaBridge
import com.example.myluaapplication.util.addFragment
import com.example.myluaapplication.util.backFragment
import java.lang.IllegalStateException
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val dynamicBtn by lazy { findViewById<Button>(R.id.generate_dynamic_msg_btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadLuaFiles()
        provideLibrariesImplementations()

        addFragment(HomeFragment.newInstance())
    }

    override fun onResume() {
        super.onResume()
        dynamicBtn.setOnClickListener {
            val r = Random.nextInt(1, 1000)
            LuaBridge.Messages.showDynamicMessage(r.toString())
        }
    }

    private fun loadLuaFiles() {
        LuaBridge.processLuaFiles(
            assets,
            "navigation.lua",
            "dynamic_message.lua"
        )
    }

    private fun provideLibrariesImplementations() {
        // Navigation for screens without, or with static parameters
        NavigationLib.mainNavigation = { navCode ->
            when (navCode) {
                "FragmentA" -> addFragment(FragmentA.newInstance())
                "FragmentB" -> addFragment(FragmentB.newInstance())
                "Back"      -> backFragment()
                else        -> throw IllegalStateException()
            }
        }

        // Requires special navigation for screen with dynamic parameters
        NavigationLib.navigateToSuccess = { message ->
            addFragment(SuccessFragment.newInstance(message))
        }

        // Contract for GUI operations
        GuiLib.guiContract = object: GuiContract {
            override fun showToast(msg: String) {
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

            override fun showMessage(msg: String) {
                // Message is shown different between screens
                val lastFrag = getLastGuiAware()

                if (lastFrag is GuiAware) {
                    lastFrag.onGuiMessage(msg)
                }
            }
        }
    }

    private fun getLastGuiAware(): GuiAware? {
        return supportFragmentManager
            .fragments
            .filterIsInstance(GuiAware::class.java)
            .lastOrNull()
    }

}