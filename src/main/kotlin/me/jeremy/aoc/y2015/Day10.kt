package me.jeremy.aoc.y2015

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day10: Day<String, Int> {
    override fun runPartOne(): Int {
        var lk = getInput()
        repeat(40) {
            var nlk = ""
            var i = 0
            while (i < lk.length) {
                val c = lk[i]
                var n = 0
                while (i + n < lk.length && lk[i + n] == c) {
                    n++
                }
                i += n
                nlk += "$n$c"
            }
            lk = nlk
        }
        println(lk)
        return lk.length
    }

    override fun runPartTwo(): Int {
        TODO("Not yet implemented")
    }

    override fun getInput(): String = AOCUtils.getDayInput(2015, 10).first()
}

fun main() {
    val day = Day10()
    println(day.runPartOne())
}
