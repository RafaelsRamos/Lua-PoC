package com.example.myluaapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myluaapplication.lua.LuaBridge
import com.example.myluaapplication.R
import com.example.myluaapplication.ui.GuiAware

class FragmentA : Fragment(), GuiAware {

    private val amountEd by lazy { requireView().findViewById<EditText>(R.id.et_amount) }
    private val dynamicTv by lazy { requireView().findViewById<TextView>(R.id.tv_dynamic_msg) }
    private val backId by lazy { requireView().findViewById<View>(R.id.back_iv) }
    private val successBtn by lazy { requireView().findViewById<Button>(R.id.success_btn) }
    private val failureBtn by lazy { requireView().findViewById<Button>(R.id.failure_btn) }

    private val amount get() = amountEd.text.toString().toDoubleOrNull() ?: 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backId.setOnClickListener {
            LuaBridge.Navigation.backPressed()
        }
        successBtn.setOnClickListener {
            LuaBridge.Actions.performServiceA(success = true, amount)
        }
        failureBtn.setOnClickListener {
            LuaBridge.Actions.performServiceA(success = false, amount)
        }
    }

    override fun onGuiMessage(message: String) {
        dynamicTv.setText(message)
    }

    companion object {
        fun newInstance() = FragmentA()
    }

}