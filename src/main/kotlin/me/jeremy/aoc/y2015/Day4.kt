package me.jeremy.aoc.y2015

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day
import java.math.BigInteger
import java.security.MessageDigest

class Day4 : Day<String, Int> {
    override fun runPartOne(): Int = findNumber(5)

    override fun runPartTwo(): Int = findNumber(6)

    override fun getInput(): String = AOCUtils.getDayInput(2015, 4).first()

    private fun md5(s: String): String =
        BigInteger(1, MessageDigest.getInstance("MD5").digest(s.toByteArray()))
            .toString(16)
            .padStart(32, '0')

    private fun findNumber(k: Int): Int {
        val sk = getInput()
        var n = 1
        while (!md5("$sk$n").startsWith("0".repeat(k))) {
            n++
        }
        return n
    }

}

fun main() {
    val day = Day4()
    println(day.runPartOne())
    println(day.runPartTwo())
}
