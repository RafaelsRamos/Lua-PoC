LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")

-- defines a factorial function
function change_age_and_print_person(person)
    person = { name = "new name", age = person.age + 1 }
    LuajLog:d("person name: "..person.name)
    LuajLog:d("person age: "..person.age)
    return person
end