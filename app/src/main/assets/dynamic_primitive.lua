LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")
CustomValue = luakotlin.bindClass("com.example.myluaapplication.component.CustomValue")

function show_toast_with_custom_value(value)
    LuajLog:d(string.format('value : '..value))
end
