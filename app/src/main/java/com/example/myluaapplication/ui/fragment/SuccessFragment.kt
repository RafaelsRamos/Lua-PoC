package com.example.myluaapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.myluaapplication.R
import com.example.myluaapplication.ui.GuiAware

private const val ARG_MSG = "param_msg"

class SuccessFragment : Fragment(), GuiAware {

    private val messageTv by lazy { requireView().findViewById<TextView>(R.id.tv_message) }

    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString(ARG_MSG)
    }

    override fun onCreateView(inf: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inf.inflate(R.layout.fragment_success, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        messageTv.text = message
    }

    override fun onGuiMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(message: String) = SuccessFragment().apply {
            arguments = bundleOf(ARG_MSG to message)
        }
    }

}