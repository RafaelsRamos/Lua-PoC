package com.example.myluaapplication

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myluaapplication.logcat.LogcatObserver
import com.example.myluaapplication.model.Person
import com.example.myluaapplication.model.toPerson
import com.github.only52607.luakt.toLua
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

        findViewById<Button>(R.id.btn_show_toast_from_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_simple_lua_file).setOnClickListener(this)
        findViewById<Button>(R.id.btn_lua_file_with_custom_library).setOnClickListener(this)
        findViewById<Button>(R.id.btn_lua_file_that_uses_singleton).setOnClickListener(this)
        findViewById<Button>(R.id.btn_pass_primitive_to_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_pass_complex_instance_to_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_process_complex_obj_from_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_process_primitive_from_lua).setOnClickListener(this)
        findViewById<Button>(R.id.btn_lua_on_background_Thread).setOnClickListener(this)
        findViewById<TextView>(R.id.logcat_clear).setOnClickListener(this)

        setupLogcatUpdates()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_show_toast_from_lua                 -> {
                processLuaContent(LuaScripts.commandOne)
                globals
                    .get("show_toast_from_lua")
                    .call(baseContext.toLua())
            }
            R.id.btn_simple_lua_file                -> processLuaFile("simple_script.lua")
            R.id.btn_lua_file_with_custom_library   -> processLuaFile("hyperbolic_app.lua")
            R.id.btn_lua_file_that_uses_singleton   -> invokeLuaFunction()
            R.id.btn_pass_primitive_to_lua          -> passPrimitiveInput()
            R.id.btn_pass_complex_instance_to_lua   -> passComplexInput()
            R.id.btn_process_complex_obj_from_lua   -> processComplexOutput()
            R.id.btn_process_primitive_from_lua     -> processPrimitiveOutput()
            R.id.btn_lua_on_background_Thread       -> runScriptOnBackground()
            R.id.logcat_clear                       -> LogcatObserver.clear()
        }
    }

    private fun invokeLuaFunction() {
        processLuaFile("invoke_function.lua")
        println("Calling invoke_function.lua's print_arr function from Kotlin")
        globals
            .get("print_arr")
            .call()
    }

    private fun passPrimitiveInput() {
        hideKeyboard()
        processLuaFile("dynamic_primitive.lua")
        globals
            .get("show_toast_with_custom_value")
            .call(inputText.toLua())
    }

    private fun passComplexInput() {
        processLuaFile("complex_input.lua")
        val john = Person(name = "John", age = 30)
        println("Passing $john to lua")
        globals
            .get("print_person")
            .call(john.toLua())
    }

    private fun processComplexOutput() {
        processLuaFile("complex_output.lua")
        val result = globals
            .get("tweak_and_return_person")
            .call()

        val olderJohn = result.toPerson()

        println("Result type: ${result::class.simpleName}")
        println("Mapped to $olderJohn")
    }

    private fun processPrimitiveOutput() {
        processLuaFile("process_output_primitive.lua")
        val result: Varargs = globals
            .get("get_word")
            .call()
        println("Result type: ${result::class.simpleName}")
        println("Casting to String: \"${result.tojstring()}\"")
    }

    private fun runScriptOnBackground() {
        processLuaFile("run_on_background.lua")

        CoroutineScope(Dispatchers.IO).launch {
            println("Calling lua function from background thread")
            globals.get("lua_function").call(LuaInteger.valueOf(5))
            println("Finished calling lua function from background thread")
        }
    }

    private fun processLuaFile(fileName: String): Varargs {
        val inputStream: InputStream = assets.open(fileName)
        val chuck: LuaValue = globals.load(inputStream, "@$fileName", "bt", globals)
        return chuck.invoke().also { println("Processed $fileName") }
    }

    private fun processLuaContent(content: String): Varargs {
        val chuck: LuaValue = globals.load(content, "content")
        return chuck.invoke()
    }

    private fun setupLogcatUpdates() {
        LogcatObserver.subscribe {
            findViewById<TextView>(R.id.logcat_text_view).text = it
        }
    }

}