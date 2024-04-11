package com.example.myluaapplication.lua.libs

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction

interface GuiContract {
    fun showToast(msg: String)
    fun showMessage(msg: String)
}

object GuiLib: TwoArgFunction() {

    lateinit var guiContract: GuiContract

    private object ShowToastFunc: OneArgFunction() {
        override fun call(arg: LuaValue): LuaValue {
            guiContract.showToast(arg.checkjstring())
            return NIL
        }
    }

    private object ShowMessageFunc: OneArgFunction() {
        override fun call(arg: LuaValue): LuaValue {
            guiContract.showMessage(arg.checkjstring())
            return NIL
        }
    }

    override fun call(modName: LuaValue, env: LuaValue): LuaValue {
        val library: LuaValue = tableOf()
        library["show_toast"] = ShowToastFunc
        library["show_message"] = ShowMessageFunc
        env.set("gui", library)
        env.get("package").get("loaded").set("gui", library)
        return library
    }
}

