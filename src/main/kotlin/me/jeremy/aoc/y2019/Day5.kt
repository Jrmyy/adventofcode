package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day5: IntCodeProgram(), Day<List<Long>, Long> {

    override fun runPartOne(): Long {
        val outputs = runIntCodeProgram(getInput().toMutableList(), listOf(1)).second
        return outputs[outputs.size - 1]
    }


    override fun runPartTwo(): Long =
        runIntCodeProgram(getInput().toMutableList(), listOf(5)).second[0]

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 5)[0].split(",").map { it.toLong() }

}

fun main() {
    val day = Day5()
    println(day.runPartOne())
    println(day.runPartTwo())
}
