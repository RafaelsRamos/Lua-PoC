Thread = luakotlin.bindClass("java.lang.Thread")
LuajLog = luakotlin.bindClass("com.example.myluaapplication.component.LuajLog")

function lua_function(seconds_delay)
    LuajLog:d("Thread: " .. tostring(Thread:currentThread()))
    for i = 1, seconds_delay do
        co.sleep(1000)
        LuajLog:d("seconds elapsed: " .. tostring(i))
    end
end