import com.github.only52607.luakt.KotlinClass
import com.github.only52607.luakt.lib.LuaKotlinLib
import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaString
import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.*
import org.luaj.vm2.lib.jse.JseBaseLib
import org.luaj.vm2.lib.jse.JseIoLib
import org.luaj.vm2.lib.jse.JseMathLib
import org.luaj.vm2.lib.jse.JseOsLib

class LuaKotlinTest {
    @org.junit.Test
    fun basicTest() {
        val g = ktGlobals()
        val cat = g.load(
            LuaKotlinTest::class.java.getResourceAsStream("lua/basic.lua"),
            "basic.lua",
            "t",
            g
        ).invoke()

        val castCat = cat as KotlinClass
        val age = castCat.getProperty("age")!!
        println(age.getter)
    }

    private fun ktGlobals(): Globals {
        val globals = Globals()
        globals.load(JseBaseLib())
        globals.load(PackageLib())
        globals.load(Bit32Lib())
        globals.load(TableLib())
        globals.load(StringLib())
        globals.load(CoroutineLib())
        globals.load(JseMathLib())
        globals.load(JseIoLib())
        globals.load(JseOsLib())
        globals.load(LuaKotlinLib())
        LoadState.install(globals)
        LuaC.install(globals)
        return globals
    }
}