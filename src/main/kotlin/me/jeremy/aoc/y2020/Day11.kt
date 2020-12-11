package me.jeremy.aoc.y2020

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Map(private val coordinates: List<MutableList<Char>>) {

    private fun getSeatsCountInAdjacentPositions(x: Int, y: Int, state: List<MutableList<Char>>): Int =
        listOf(
            Pair(x - 1, y),
            Pair(x - 1, y - 1),
            Pair(x - 1, y + 1),
            Pair(x + 1, y),
            Pair(x + 1, y - 1),
            Pair(x + 1, y + 1),
            Pair(x, y - 1),
            Pair(x, y + 1)
        ).filter {
           it.second in state.indices && it.first in state[0].indices
        }.count { state[it.second][it.first] == '#' }

    private fun getSeatsCountInAdjacentDirections(x: Int, y: Int, state: List<MutableList<Char>>): Int =
        listOf(
            Pair(-1, -1),
            Pair(0, -1),
            Pair(1, -1),
            Pair(-1, 0),
            Pair(1, 0),
            Pair(-1, 1),
            Pair(0, 1),
            Pair(1, 1)
        ).mapNotNull {
            var currentX = x + it.first
            var currentY = y + it.second
            var hasFoundBeforeTerminating = false
            while (currentX in state[0].indices && currentY in state.indices) {
                if (state[currentY][currentX] != '.') {
                    hasFoundBeforeTerminating = true
                    break
                }
                currentX += it.first
                currentY += it.second
            }
            if (hasFoundBeforeTerminating) {
                state[currentY][currentX]
            } else {
                null
            }
        }.count { it == '#' }

    fun copy() = Map(coordinates.map { it.toMutableList() }.toList())

    fun updateMap(wideSearch: Boolean, seatsCountToSwitchToEmpty: Int) {
        val initialState = copy()
        coordinates.forEachIndexed {
                y: Int, mutableList: MutableList<Char> ->
            mutableList.forEachIndexed { x, c ->
                val count = if(wideSearch) {
                    getSeatsCountInAdjacentDirections(x, y, initialState.coordinates)
                } else {
                    getSeatsCountInAdjacentPositions(x, y, initialState.coordinates)
                }
                if (c == '#' && count >= seatsCountToSwitchToEmpty) {
                    coordinates[y][x] = 'L'
                } else if (c == 'L' && count == 0) {
                    coordinates[y][x] = '#'
                }
            }
        }
    }

    fun getOccupiedSeatsCount(): Int = coordinates.flatten().count { it == '#' }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Map) return false
        return this.coordinates == other.coordinates
    }

    override fun hashCode(): Int {
        return coordinates.hashCode()
    }
}

class Day11: Day<Map, Int> {
    override fun runPartOne(): Int = doRun()

    override fun runPartTwo(): Int = doRun(true, 5)

    override fun getInput(): Map = Map(
        AOCUtils.getDayInput(2020, 11).map { it.toList().toMutableList() }
    )

    private fun doRun(wideSearch: Boolean = false, seatsCountToSwitchToEmpty: Int = 4): Int {
        val map = getInput()
        var lastMap = map.copy()
        var hasDone = false
        while (!hasDone) {
            val beforeMap = map.copy()
            map.updateMap(wideSearch, seatsCountToSwitchToEmpty)
            lastMap = map.copy()
            hasDone = lastMap == beforeMap
        }
        return lastMap.getOccupiedSeatsCount()
    }
}

fun main() {
    val day = Day11()
    println(day.runPartOne())
    println(day.runPartTwo())
}
