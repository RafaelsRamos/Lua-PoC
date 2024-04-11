package com.example.myluaapplication.lua

import com.example.myluaapplication.lua.libs.GuiLib
import com.example.myluaapplication.lua.libs.NavigationLib
import com.github.only52607.luakt.lib.KotlinCoroutineLib
import com.github.only52607.luakt.lib.LuaKotlinLib
import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaValue
import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.Bit32Lib
import org.luaj.vm2.lib.CoroutineLib
import org.luaj.vm2.lib.PackageLib
import org.luaj.vm2.lib.StringLib
import org.luaj.vm2.lib.TableLib
import org.luaj.vm2.lib.jse.JseBaseLib
import org.luaj.vm2.lib.jse.JseIoLib
import org.luaj.vm2.lib.jse.JseMathLib
import org.luaj.vm2.lib.jse.JseOsLib

fun ktGlobals(): Globals {
    return Globals().apply {
        loadAll(
            JseBaseLib(),
            PackageLib(),
            Bit32Lib(),
            TableLib(),
            StringLib(),
            CoroutineLib(),
            KotlinCoroutineLib(),
            JseMathLib(),
            JseIoLib(),
            JseOsLib(),
            LuaKotlinLib(),
            NavigationLib,
            GuiLib
        )
        LoadState.install(this)
        LuaC.install(this)
    }
}

fun Globals.loadAll(vararg libs: LuaValue) {
    libs.forEach { load(it) }
}