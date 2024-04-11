package com.example.myluaapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.myluaapplication.R
import com.example.myluaapplication.lua.LuaBridge
import com.example.myluaapplication.ui.GuiAware


class FragmentB : Fragment(), GuiAware {

    private val backIv by lazy { requireView().findViewById<ImageView>(R.id.back_iv) }

    override fun onCreateView(inf: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inf.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backIv.setOnClickListener {
            LuaBridge.Navigation.backPressed()
        }
    }

    override fun onGuiMessage(message: String) {
        AlertDialog
            .Builder(requireContext())
            .create()
            .apply {
                setTitle("Message")
                setMessage(message)
                setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ -> dialog.dismiss() }
            }
            .show()
    }

    companion object {
        fun newInstance() = FragmentB()
    }

}