package net.berkayak.weatherobserver

import android.os.Debug
import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.io.Console
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dateFormat(){
        val now = 1580393350598L
        val formatted = SimpleDateFormat("dd/MM/yyyy HH:mm").format(now)
        assertEquals("30/01/2020 17:09", formatted)

    }
}
