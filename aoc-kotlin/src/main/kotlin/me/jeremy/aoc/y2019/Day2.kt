package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day2 : IntCodeProgram(), Day<List<Long>, Int> {
    override fun runPartOne(): Int = doSim(12, 2)

    override fun runPartTwo(): Int {
        for (noun in (0..99)) {
            for (verb in (0..99)) {
                if (doSim(noun, verb) == 19690720) {
                    return 100 * noun + verb
                }
            }
        }
        throw RuntimeException("No noun and verb combination found")
    }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 2)[0].split(",").map { it.toLong() }

    private fun doSim(noun: Int, verb: Int): Int {
        val codes = getInput().toMutableList()
        codes[1] = noun.toLong()
        codes[2] = verb.toLong()
        return runIntCodeProgram(codes, hasOptMode = false).outputs[0].toInt()
    }
}

fun main() {
    val day = Day2()
    println(day.runPartOne())
    println(day.runPartTwo())
}
