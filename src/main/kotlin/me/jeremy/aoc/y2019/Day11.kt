package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day11 : IntCodeProgram(), Day<List<Long>, Int> {
    override fun runPartOne(): Int =
        getCoordinates(0L).count { it.value.contains(1) }

    private fun getCoordinates(startColor: Long): MutableMap<Pair<Int, Int>, MutableList<Int>> {
        val coordinates = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
        var currentCoord = Pair(0, 0)
        var currentDirection = 'U'
        val software = getInput()
        var inputs = listOf(startColor)
        var currentRes = IntCodeProgramResult(
            software.toMutableList(),
            0,
            0,
            0,
            mutableListOf(-1)
        )
        while (currentRes.outputs.isNotEmpty()) {
            val res = runIntCodeProgram(
                currentRes.codes.toMutableList(),
                inputs,
                initialCurrentIdx = currentRes.currentIdx,
                initialCurrentIptIdx = currentRes.currentIptIdx,
                initialRelativeBase = currentRes.relativeBase,
                maxOutputSize = 2,
                defaultIfNotEnoughInput = inputs[0]
            )
            if (res.outputs.size != 2) {
                break
            }
            val (color, turn) = res.outputs.map { it.toInt() }
            val colors = coordinates.getOrDefault(currentCoord, mutableListOf())
            colors.add(color)
            coordinates[currentCoord] = colors
            currentRes = res
            val (newCoord, newDirection) = getDirectionAndPos(currentDirection, currentCoord, turn)
            currentDirection = newDirection
            currentCoord = newCoord
            val lastColor = coordinates
                .getOrDefault(currentCoord, mutableListOf())
                .lastOrNull() ?: 0
            inputs = listOf(lastColor.toLong())
        }
        return coordinates
    }

    private fun getDirectionAndPos(
        currentDirection: Char,
        currentPos: Pair<Int, Int>,
        turn: Int
    ): Pair<Pair<Int, Int>, Char> =
        when (currentDirection) {
            'U' -> if (turn == 0) {
                Pair(Pair(currentPos.first - 1, currentPos.second), 'L')
            } else {
                Pair(Pair(currentPos.first + 1, currentPos.second), 'R')
            }
            'D' -> if (turn == 0) {
                Pair(Pair(currentPos.first + 1, currentPos.second), 'R')
            } else {
                Pair(Pair(currentPos.first - 1, currentPos.second), 'L')
            }
            'R' -> if (turn == 0) {
                Pair(Pair(currentPos.first, currentPos.second + 1), 'U')
            } else {
                Pair(Pair(currentPos.first, currentPos.second - 1), 'D')
            }
            else -> if (turn == 0) {
                Pair(Pair(currentPos.first, currentPos.second - 1), 'D')
            } else {
                Pair(Pair(currentPos.first, currentPos.second + 1), 'U')
            }
        }

    override fun runPartTwo(): Int {
        val coordinates = getCoordinates(1L).mapValues { it.value.last() }
        val minX = coordinates.minOf { it.key.first }
        val maxX = coordinates.maxOf { it.key.first }
        val minY = coordinates.minOf { it.key.second }
        val maxY = coordinates.maxOf { it.key.second }
        val drawing = mutableListOf<List<String>>()
        for (j in minY until maxY + 1) {
            val current = mutableListOf<String>()
            for (i in minX until maxX + 1) {
                if (coordinates.containsKey(Pair(i, j))) {
                    val color = coordinates[Pair(i, j)]
                    if (color == 0) {
                        current.add(" ")
                    } else {
                        current.add("*")
                    }
                } else {
                    current.add(" ")
                }
            }
            drawing.add(current)
        }
        drawing.reversed().forEach {
            println(it.joinToString(""))
        }
        return 0
    }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 11)[0].split(",").map { it.toLong() }
}

fun main() {
    val day = Day11()
    println(day.runPartOne())
    println(day.runPartTwo())
}
