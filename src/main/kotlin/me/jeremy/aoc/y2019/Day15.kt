package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day15 : IntCodeProgram(), Day<List<Long>, Int> {

    override fun runPartOne(): Int =
        getDistancesFromOxygenTank()[Pair(0, 0)] ?: throw Exception("Should have come back to the beginning")

    override fun runPartTwo(): Int =
        getDistancesFromOxygenTank().maxOf { it.value }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 15)[0]
        .split(",")
        .map { it.toLong() }

    private fun getDistancesFromOxygenTank(): Map<Pair<Int, Int>, Int> {
        val locations = mutableMapOf<Pair<Int, Int>, Long>()
        val findTankBfs = mutableListOf(Triple(0, 0, getInput().toMutableList()))
        val directions = mapOf(
            1L to Pair(0, 1),
            2L to Pair(0, -1),
            3L to Pair(-1, 0),
            4L to Pair(1, 0),
        )
        var oTank: Pair<Int, Int>? = null

        while (findTankBfs.isNotEmpty()) {
            val (x, y, xyCodes) = findTankBfs.removeFirst()
            directions.forEach { (di, dxy) ->
                val nxy = Pair(x + dxy.first, y + dxy.second)
                if (!locations.containsKey(nxy)) {
                    val run = runIntCodeProgram(xyCodes.toMutableList(), listOf(di))
                    val droidOpt = run.outputs.first()
                    locations[nxy] = droidOpt
                    if (droidOpt == 2L) {
                        oTank = nxy
                    }
                    if (droidOpt != 0L) {
                        findTankBfs.add(Triple(nxy.first, nxy.second, run.codes))
                    }
                }
            }
        }

        if (oTank == null) {
            throw Exception("oTank should not be null now")
        }

        val cleanMoves = mutableMapOf(oTank!! to 0)
        val leastMovesBfs = mutableListOf(oTank!!)
        while (leastMovesBfs.isNotEmpty()) {
            val currentPosition = leastMovesBfs.removeFirst()
            val (x, y) = currentPosition
            directions.forEach { (_, dxy) ->
                val nxy = Pair(x + dxy.first, y + dxy.second)
                if (!cleanMoves.containsKey(nxy)) {
                    if (locations[nxy] == 1L) {
                        cleanMoves[nxy] = cleanMoves[currentPosition]!! + 1
                        leastMovesBfs.add(nxy)
                    }
                }
            }
        }
        return cleanMoves
    }
}

fun main() {
    val day = Day15()
    println(day.runPartOne())
    println(day.runPartTwo())
}
