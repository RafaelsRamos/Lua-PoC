package com.example.myluaapplication.lua.libs

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction

object NavigationLib: TwoArgFunction() {

    var mainNavigation: (String) -> Unit = { }

    var navigateToSuccess: (msg: String) -> Unit = { }

    private val navigationFunc = object : OneArgFunction() {
        override fun call(arg: LuaValue): LuaValue {
            mainNavigation.invoke(arg.checkjstring())
            return NIL
        }
    }

    private val navigateToSuccessFunc = object : OneArgFunction() {
        override fun call(arg: LuaValue): LuaValue {
            navigateToSuccess.invoke(arg.checkjstring())
            return NIL
        }
    }

    override fun call(modName: LuaValue, env: LuaValue): LuaValue {
        val library: LuaValue = tableOf()
        library["navigate"] = navigationFunc
        library["navigate_to_success"] = navigateToSuccessFunc
        env.set("navigation", library)
        env.get("package").get("loaded").set("navigation", library)
        return library
    }
}