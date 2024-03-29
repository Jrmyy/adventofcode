package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day24 : Day<Day24.Eris, Int> {

    class Eris(private val coordinates: List<MutableList<Char>>) {

        fun copy() = Eris(coordinates.map { it.toMutableList() }.toList())

        fun calculateBiodiversityRating(): Int {
            var currentMultiplier = 1
            return coordinates.flatten().mapNotNull {
                val res = if (it == '#') {
                    currentMultiplier
                } else {
                    null
                }
                currentMultiplier *= 2
                res
            }.sum()
        }

        fun updateEris() {
            val initialState = copy()
            coordinates.forEachIndexed { y: Int, mutableList: MutableList<Char> ->
                mutableList.forEachIndexed { x, c ->
                    val count = AOCUtils.getAdjacentPositions(initialState.coordinates, y, x, false).count {
                        initialState.coordinates[it.second][it.first] == '#'
                    }
                    if (c == '#' && count != 1) {
                        coordinates[y][x] = '.'
                    } else if (c == '.' && count in (1..2)) {
                        coordinates[y][x] = '#'
                    }
                }
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Eris) return false
            return this.coordinates == other.coordinates
        }

        override fun hashCode(): Int {
            return coordinates.hashCode()
        }
    }

    override fun runPartOne(): Int {
        val map = getInput()
        val layers = mutableListOf(map.copy())
        var hasDone = false
        while (!hasDone) {
            map.updateEris()
            layers.add(map.copy())
            hasDone = layers.groupingBy { it }.eachCount().filterValues { it == 2 }.isNotEmpty()
        }
        val firstRepeatingLayer = layers.groupingBy { it }.eachCount().filterValues { it == 2 }.toList()[0].first
        return firstRepeatingLayer.calculateBiodiversityRating()
    }

    override fun runPartTwo(): Int {
        TODO("Not yet implemented")
    }

    override fun getInput(): Eris = Eris(
        AOCUtils.getDayInput(2019, 24).map { it.toList().toMutableList() }
    )
}

fun main() {
    val day = Day24()
    println(day.runPartOne())
}
