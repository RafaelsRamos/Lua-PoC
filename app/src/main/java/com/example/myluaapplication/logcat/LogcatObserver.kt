package com.example.myluaapplication.logcat

import android.os.Handler
import android.os.Looper
import java.io.BufferedReader
import java.io.InputStreamReader

object LogcatObserver {

    private val subscribers: MutableSet<(String) -> Unit> = mutableSetOf()
    private var lastUpdate: Int = 0

    init {
        clear()
    }

    fun subscribe(subscriber: (String) -> Unit) {
        subscribers.add(subscriber)
        initUpdates()
    }

    fun clear() {
        val br = getBufferedReader()

        lastUpdate = 0
        while (br.readLine() != null) {
            lastUpdate += 1
        }
    }

    private fun initUpdates() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                notifyUpdate()

                if (subscribers.isEmpty()) {
                    handler.removeCallbacks(this)
                } else {
                    handler.postDelayed(this, 200)
                }
            }
        }, 0)
    }

    private fun notifyUpdate() {
        val br = getBufferedReader()

        val logList = mutableListOf<String>()
        var line: String?
        var lineNumber = 0

        while (br.readLine().also { line = it } != null) {
            if (++lineNumber > lastUpdate) {
                val l = (lineNumber - lastUpdate).toString().padStart(3, '0')
                val lineContent = line!!.split("System.out: ").last()
                logList.add("$l || $lineContent")
            }
        }
        subscribers.forEach {
            val content = logList.reversed().joinToString(separator = "") { line -> "\n$line" }
            it.invoke(content)
        }
    }

    private fun getBufferedReader(): BufferedReader {
        val process = Runtime.getRuntime().exec("logcat -d System.out:I *:S")
        return BufferedReader(InputStreamReader(process.inputStream))
    }


}