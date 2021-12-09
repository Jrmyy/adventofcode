package me.jeremy.aoc.y2021

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day9 : Day<List<List<Int>>, Int> {
    override fun runPartOne(): Int = getDangerZones(getInput()).flatten().sum()

    override fun runPartTwo(): Int {
        val heightMap = getInput()
        val dangerZones = getDangerZones(heightMap)
        return dangerZones.asSequence().flatMapIndexed { rI, r ->
            r.mapIndexedNotNull { cI, d ->
                if (d > 0) Pair(rI, cI) else null
            }
        }.map {
            val basinPositions = mutableSetOf(it)
            val toVisit = mutableListOf(it)
            val visited = mutableListOf<Pair<Int, Int>>()
            while (toVisit.isNotEmpty()) {
                val (rI, cI) = toVisit.removeFirst()
                visited.add(Pair(rI, cI))
                val adjacent = getAdjacentPositions(heightMap, rI, cI)
                val sameBasinAdjacentPositions = adjacent.filter { p -> heightMap[p.first][p.second] != 9 }
                basinPositions.addAll(sameBasinAdjacentPositions)
                toVisit.addAll(sameBasinAdjacentPositions.filter { p -> !visited.contains(p) })
            }
            basinPositions.size
        }.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    override fun getInput(): List<List<Int>> = AOCUtils.getDayInput(2021, 9).map {
        it.split("").filter { d -> d.isNotBlank() }.map { d -> d.toInt() }
    }

    private fun getDangerZones(heightMap: List<List<Int>>): List<List<Int>> =
        heightMap.mapIndexed { rI, r ->
            r.mapIndexed { cI, d ->
                if (getAdjacentPositions(heightMap, rI, cI).map { heightMap[it.first][it.second] }.minOrNull()!! > d) {
                    d + 1
                } else {
                    0
                }
            }
        }

    private fun getAdjacentPositions(l: List<List<Int>>, rI: Int, cI: Int): List<Pair<Int, Int>> =
        if (cI == 0) {
            val adjacentForSure = mutableListOf(Pair(rI, 1))
            if (rI > 0) {
                adjacentForSure.add(Pair(rI - 1, 0))
            }
            if (rI != l.size - 1) {
                adjacentForSure.add(Pair(rI + 1, 0))
            }
            adjacentForSure
        } else if (cI == l.first().size - 1) {
            val adjacentForSure = mutableListOf(Pair(rI, cI - 1))
            if (rI > 0) {
                adjacentForSure.add(Pair(rI - 1, cI))
            }
            if (rI != l.size - 1) {
                adjacentForSure.add(Pair(rI + 1, cI))
            }
            adjacentForSure
        } else if (rI == 0) {
            listOf(Pair(0, cI - 1), Pair(0, cI + 1), Pair(1, cI))
        } else if (rI == l.size - 1) {
            listOf(Pair(rI, cI - 1), Pair(rI, cI + 1), Pair(rI - 1, cI))
        } else {
            listOf(Pair(rI + 1, cI), Pair(rI - 1, cI), Pair(rI, cI - 1), Pair(rI, cI + 1))
        }
}

fun main() {
    val day = Day9()
    println(day.runPartOne())
    println(day.runPartTwo())
}
