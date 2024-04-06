LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")

function get_word()
    local word = "\"John Smith\""
    LuajLog:d("Returning string: "..word)
    return word
end