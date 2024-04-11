package com.example.myluaapplication.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myluaapplication.lua.LuaBridge
import com.example.myluaapplication.R
import com.example.myluaapplication.ui.GuiAware
import com.google.android.material.R.id.snackbar_text
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), GuiAware {

    private val btnServiceA by lazy { requireView().findViewById<Button>(R.id.btn_service_a) }
    private val btnServiceB by lazy { requireView().findViewById<Button>(R.id.btn_service_b) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnServiceA.setOnClickListener {
            LuaBridge.Navigation.serviceAClicked()
        }
        btnServiceB.setOnClickListener {
            LuaBridge.Navigation.serviceBClicked()
        }
    }

    override fun onGuiMessage(message: String) {
        Snackbar
            .make(requireView(), message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).apply {
                setActionTextColor(Color.BLUE)
                this.view.setBackgroundColor(Color.LTGRAY)
                this.view.findViewById<TextView>(snackbar_text).apply {
                    setTextColor(Color.BLUE)
                    textSize = 28f
                }
            }
            .show()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }



}