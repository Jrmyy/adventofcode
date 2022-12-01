package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day9 : IntCodeProgram(), Day<List<Long>, Long> {
    override fun runPartOne(): Long =
        runIntCodeProgram(getInput().toMutableList(), listOf(1)).outputs[0]

    override fun runPartTwo(): Long =
        runIntCodeProgram(getInput().toMutableList(), listOf(2)).outputs[0]

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 9)[0].split(",").map { it.toLong() }
}

fun main() {
    val day = Day9()
    println(day.runPartOne())
    println(day.runPartTwo())
}
