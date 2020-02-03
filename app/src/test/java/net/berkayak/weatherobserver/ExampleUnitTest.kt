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

    @Test
    fun fourt(){
        val number = 77

        var left = number
        var digit = 0

        var i = 0
        while (i > 4) {
            println("i:$i")
            digit = left % 2
            left = left / 2
            i++
            System.out.println("i: " + i + ", digit: " + digit + ", left: " + left);
        }

        assertEquals(digit, 1)
    }
}
