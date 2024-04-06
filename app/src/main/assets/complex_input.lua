LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")

function print_person(person)
    LuajLog:d("person name: "..person.name)
    LuajLog:d("person age: "..person.age)
end