LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")

function tweak_and_return_person()
    local person = { name = "Jane doe", age = 15 }
    LuajLog:d("Returning table with name = \""..person.name.."\" and age = "..person.age)
    return person
end