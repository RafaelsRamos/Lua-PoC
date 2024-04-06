package com.example.myluaapplication.component

import android.content.Context
import android.widget.Toast

class LuajLog {

    companion object {
        fun d(msg: String) {
            println("Lua: $msg")
        }

        fun toast(msg: String, context: Context) {
            Toast.makeText(context, "Lua: $msg", Toast.LENGTH_SHORT).show()
        }

    }

}