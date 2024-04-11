package com.example.myluaapplication.util

import android.content.res.AssetManager
import org.luaj.vm2.Globals
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import java.io.InputStream

fun AssetManager.processLuaFile(globals: Globals, fileName: String): Varargs {
    val inputStream: InputStream = open(fileName)
    val chuck: LuaValue = globals.load(inputStream, "@$fileName", "bt", globals)
    return chuck.invoke().also { println("Processed $fileName") }
}