package me.jeremy.aoc.y2016

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day12 : Day<List<String>, Int> {

    override fun runPartOne(): Int = compute(mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0))

    override fun runPartTwo(): Int = compute(mutableMapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0))

    override fun getInput(): List<String> = AOCUtils.getDayInput(2016, 12)

    private fun compute(registers: MutableMap<String, Int>): Int {
        val instructions = getInput()
        var i = 0
        while (i < instructions.size) {
            val instruction = instructions[i]
            if (instruction.startsWith("cpy")) {
                val (from, to) = instruction.replace("cpy ", "").split(" ")
                if ("\\d+".toRegex().matches(from)) {
                    registers[to] = from.toInt()
                } else {
                    registers[to] = registers[from]!!
                }
                i++
            } else if (instruction.startsWith("inc")) {
                instruction.replace("inc ", "").let { inc ->
                    registers[inc] = registers[inc]!! + 1
                }
                i++
            } else if (instruction.startsWith("dec")) {
                instruction.replace("dec ", "").let { dec ->
                    registers[dec] = registers[dec]!! - 1
                }
                i++
            } else {
                val (x, y) = instruction.replace("jnz ", "").split(" ")
                val shouldJump = (if ("\\d+".toRegex().matches(x)) x.toInt() else registers[x]!!) != 0
                if (shouldJump) {
                    i += y.toInt()
                } else {
                    i++
                }
            }
        }
        return registers["a"]!!
    }
}

fun main() {
    val day = Day12()
    println(day.runPartOne())
    println(day.runPartTwo())
}
