package com.example.myluaapplication.model

import org.luaj.vm2.LuaValue
import org.luaj.vm2.LuaValue.NIL

data class Person(val name: String, val age: Int)

fun LuaValue.toPerson(): Person {
    assertConversion("name", "age")
    return Person(
        name = this["name"].tojstring(),
        age = this["age"].toint()
    )
}

fun LuaValue.assertConversion(vararg mandatoryFields: String) {
    val nilFields = mandatoryFields.filter { this[it] == NIL }
    if (nilFields.isNotEmpty()) {
        throw IllegalArgumentException("LuaValue does not contain all mandatory fields: ${nilFields.joinToString()}")
    }
}