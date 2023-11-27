package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day19 : Day<List<Long>, Long>, IntCodeProgram() {
    override fun runPartOne(): Long {
        val codes = getInput().toMutableList()
        codes.addAll((0..10).map { 0L })
        return (0L until 50L).sumOf { x ->
            (0L until 50L).sumOf { y ->
                runIntCodeProgram(codes.toMutableList(), listOf(x, y), maxOutputSize = 1).outputs.first()
            }
        }
    }

    override fun runPartTwo(): Long {
        val codes = getInput().toMutableList()
        codes.addAll((0..10).map { 0L })
        var x = 100L
        var y = 200L
        var startPosition: Pair<Long, Long>? = null
        while (startPosition == null) {
            var tl = runIntCodeProgram(codes.toMutableList(), listOf(x, y), maxOutputSize = 1).outputs.first()
            while (tl != 1L) {
                x++
                tl = runIntCodeProgram(codes.toMutableList(), listOf(x, y), maxOutputSize = 1).outputs.first()
            }
            val firstMatchingX = x
            var tr = runIntCodeProgram(codes.toMutableList(), listOf(x, y), maxOutputSize = 1).outputs.first()
            while (tr == 1L) {
                x++
                tr = runIntCodeProgram(codes.toMutableList(), listOf(x, y), maxOutputSize = 1).outputs.first()
            }
            val bl =
                runIntCodeProgram(codes.toMutableList(), listOf(x - 100L, y + 99L), maxOutputSize = 1).outputs.first()
            if (bl == 1L) {
                startPosition = Pair(x - 100L, y)
            }
            if (startPosition == null) {
                y++
                x = firstMatchingX
            }
        }
        return startPosition.first * 10_000 + startPosition.second
    }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 19)[0]
        .split(",")
        .map { it.toLong() }
}

fun main() {
    val day = Day19()
    println(day.runPartOne())
    println(day.runPartTwo())
}
