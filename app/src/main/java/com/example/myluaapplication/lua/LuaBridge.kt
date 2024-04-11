package com.example.myluaapplication.lua

import android.content.res.AssetManager
import com.example.myluaapplication.util.processLuaFile
import com.github.only52607.luakt.toLua
import org.luaj.vm2.Globals

object LuaBridge {

    private val globals: Globals = ktGlobals()

    fun processLuaFiles(assetManager: AssetManager, vararg fileNames: String) {
        fileNames.forEach { assetManager.processLuaFile(globals, it) }
    }

    object Navigation {

        fun serviceAClicked() = serviceButtonPress(action = "ServiceA")
        fun serviceBClicked() = serviceButtonPress(action = "ServiceB")
        fun backPressed() = serviceButtonPress(action = "Back")

        private fun serviceButtonPress(action: String) {
            globals.get("invoked_service_navigation").invoke(action.toLua())
        }

    }

    object Actions {

        fun performServiceA(success: Boolean, amount: Double) {
            globals.get("invoke_service_a").invoke(success.toLua(), amount.toLua())
        }

    }

    object Messages {

        fun showDynamicMessage(message: String) {
            globals.get("update_dynamic_message").invoke(message.toLua())
        }

    }

}

