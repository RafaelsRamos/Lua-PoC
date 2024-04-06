LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")

-- defines a factorial function
function parameterless_function()
    LuajLog:d("person name: "..person.name)
    LuajLog:d("person age: "..person.age)
    return person
end