package com.example.myluaapplication

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myluaapplication.component.CustomValue
import com.example.myluaapplication.model.Person
import com.example.myluaapplication.model.toPerson
import com.github.only52607.luakt.CoerceKotlinToLua
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.luaj.vm2.Globals
import org.luaj.vm2.LuaInteger
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import java.io.InputStream

class MainActivity : AppCompatActivity(), OnClickListener {

    private val globals: Globals = ktGlobals()
    private val inputText get() = findViewById<EditText>(R.id.edt_custom_input).text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_string_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_file_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_file_custom_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_file_input_to_luo).setOnClickListener(this)
        findViewById<Button>(R.id.btn_file_custom_input_to_luo).setOnClickListener(this)
        findViewById<Button>(R.id.btn_custom_arg).setOnClickListener(this)
        findViewById<Button>(R.id.btn_change_custom_arg).setOnClickListener(this)
        findViewById<Button>(R.id.btn_run_on_background).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_string_lua                 -> processLuaContent(LuaScripts.commandOne)
            R.id.btn_file_lua                   -> processLuaFile("SimpleExample.lua")
            R.id.btn_file_custom_lua            -> processLuaFile("HyperbolicApp.lua")
            R.id.btn_file_input_to_luo          -> processLuaFile("ListExample.lua")
            R.id.btn_file_custom_input_to_luo   -> passDataThroughSingleton()
            R.id.btn_custom_arg                 -> customInput()
            R.id.btn_change_custom_arg          -> processTable()
            R.id.btn_run_on_background          -> runScriptOnBackground()
        }
    }

    private fun passDataThroughSingleton() {
        CustomValue.myValue = Integer.parseInt(inputText)
        processLuaFile("CustomValue.lua")
    }

    private fun customInput() {
        processLuaFile("CustomArg.lua")
        val john = Person(name = "John", age = 30)
        globals.get("print_person").call(CoerceKotlinToLua.coerce(john))
    }

    private fun processTable() {
        processLuaFile("ChangeArg.lua")
        val john = Person(name = "John", age = 30)
        val olderJohn = globals
            .get("change_age_and_print_person")
            .call(CoerceKotlinToLua.coerce(john))
            .toPerson()
        println(olderJohn)
    }

    private fun runScriptOnBackground() {
        processLuaFile("BackgroundExample.lua")

        println("Calling lua function from main thread")
        globals.get("lua_function").call(LuaInteger.valueOf(0))
        println("Finished calling lua function from main thread")

        println("-----------------------------------")

        println("Calling lua function from background thread")
        CoroutineScope(Dispatchers.IO).launch {
            globals.get("lua_function").call(LuaInteger.valueOf(5))
            println("Finished calling lua function from background thread")
        }
    }

    private fun processLuaFile(fileName: String): Varargs {
        val inputStream: InputStream = assets.open(fileName)
        val chuck: LuaValue = globals.load(inputStream, "@$fileName", "bt", globals)
        return chuck.invoke()
    }

    private fun processLuaContent(content: String): Varargs {
        val chuck: LuaValue = globals.load(content, "content")
        return chuck.invoke()
    }

}

