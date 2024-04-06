LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")
ListReturn = luakotlin.bindClass("com.example.myluaapplication.component.ListReturn")

function print_arr()
    local string_list = ListReturn:getStringTest()
    LuajLog:d("Reading array from Singleton.")
    LuajLog:d("Array length: "..#string_list)

    for i=1, #string_list do
        LuajLog:d(string.format('string_list[%d] : '..string_list[i], i))
    end
end
