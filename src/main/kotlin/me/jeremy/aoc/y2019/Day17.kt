package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day17: IntCodeProgram(), Day<List<Long>, Int> {

    override fun runPartOne(): Int {
        val codes = getInput().toMutableList()
        val intProgramOpt = runIntCodeProgram(codes).outputs
        val scaffolds = mutableListOf<MutableList<String>>(mutableListOf())
        intProgramOpt.forEach {
            val charValue = it.toInt().toChar().toString()
            if (charValue == "\n") {
                scaffolds.add(mutableListOf())
            } else {
                scaffolds.last().add(charValue)
            }
        }
        val filteredScaffolds = scaffolds.filter { it.isNotEmpty() }
        var alignmentParametersSum = 0
        filteredScaffolds.forEachIndexed { rIdx, r ->
            r.forEachIndexed { idx, it ->
                if (rIdx >= 1 && rIdx <= filteredScaffolds.size - 2 && idx >= 1 && idx <= r.size - 2) {
                    val cross = listOf(
                        it,
                        filteredScaffolds[rIdx - 1][idx],
                        filteredScaffolds[rIdx + 1][idx],
                        filteredScaffolds[rIdx][idx - 1],
                        filteredScaffolds[rIdx][idx + 1]
                    )
                    if (cross.all { listOf("#", "^").contains(it) }) {
                        alignmentParametersSum += rIdx * idx
                    }
                }
            }
        }
        return alignmentParametersSum
    }

    override fun runPartTwo(): Int {
        val codes = getInput().toMutableList()
        val intProgramOpt = runIntCodeProgram(codes).outputs
        val scaffolds = mutableListOf<MutableList<String>>(mutableListOf())
        var currentRobotPosition = Pair(0, 0)
        intProgramOpt.forEach {
            val charValue = it.toInt().toChar().toString()
            if (charValue == "\n") {
                scaffolds.add(mutableListOf())
            } else {
                scaffolds.last().add(charValue)
            }
            if (charValue == "^") {
                currentRobotPosition = Pair(scaffolds.size - 1, scaffolds.last().size - 1)
            }
        }
        val filteredScaffolds = scaffolds.filter { it.isNotEmpty() }
        val turnR = mapOf(
            Pair(0, 1) to Pair(1, 0),
            Pair(1, 0) to Pair(0, -1),
            Pair(0, -1) to Pair(-1, 0),
            Pair(-1, 0) to Pair(0, 1)
        )
        val turnL = mapOf(
            Pair(0, 1) to Pair(-1, 0),
            Pair(-1, 0) to Pair(0, -1),
            Pair(0, -1) to Pair(1, 0),
            Pair(1, 0) to Pair(0, 1)
        )
        var direction = Pair(0, -1)
        var nxy: Pair<Int, Int>
        val path = mutableListOf(Pair("L", 0))
        while(true) {
            nxy = Pair(
                currentRobotPosition.first + direction.first, currentRobotPosition.second + direction.second
            )
            if (
                nxy.first in filteredScaffolds.indices
                && nxy.second in scaffolds.first().indices
                && scaffolds[nxy.first][nxy.second] == "#"
            ) {
                currentRobotPosition = nxy
                path[path.lastIndex] = Pair(path.last().first, path.last().second + 1)
            } else {
                val tryR = turnR[direction]!!
                nxy = Pair(
                    currentRobotPosition.first + tryR.first, currentRobotPosition.second + tryR.second
                )
                if (
                    nxy.first in filteredScaffolds.indices
                    && nxy.second in scaffolds.first().indices
                    && scaffolds[nxy.first][nxy.second] == "#"
                ) {
                    currentRobotPosition = nxy
                    direction = tryR
                    path.add(Pair("R", 1))
                } else {
                    val tryL = turnL[direction]!!
                    nxy = Pair(
                        currentRobotPosition.first + tryL.first, currentRobotPosition.second + tryL.second
                    )
                    if (
                        nxy.first in filteredScaffolds.indices
                        && nxy.second in scaffolds.first().indices
                        && scaffolds[nxy.first][nxy.second] == "#"
                    ) {
                        currentRobotPosition = nxy
                        direction = tryL
                        path.add(Pair("L", 1))
                    } else {
                        break
                    }
                }
            }
        }

        // Manually find patterns in combination
        val a = listOf(
            Pair("L", 6),
            Pair("R", 8),
            Pair("L", 4),
            Pair("R", 8),
            Pair("L", 12)
        )
        val b = listOf(
            Pair("L", 12),
            Pair("R", 10),
            Pair("L", 4)
        )
        val c = listOf(
            Pair("L", 12),
            Pair("L", 6),
            Pair("L", 4),
            Pair("L", 4),
        )
        val combination = a + b + b + c + b + c + b + c + a + a
        assert(path == combination)
        val combinationInputs = "A,B,B,C,B,C,B,C,A,A".toCharArray().map { it.toInt().toLong() } + listOf(10L)
        val aInputs = "L,6,R,8,L,4,R,8,L,12".toCharArray().map { it.toInt().toLong() } + listOf(10L)
        val bInputs = "L,12,R,10,L,4".toCharArray().map { it.toInt().toLong() } + listOf(10L)
        val cInputs = "L,12,L,6,L,4,L,4".toCharArray().map { it.toInt().toLong() } + listOf(10L)
        val debugInputs = listOf('n'.toInt().toLong(), 10L)
        val inputs = combinationInputs + aInputs + bInputs + cInputs + debugInputs
        val codesToRun = getInput().toMutableList()
        codesToRun[0] = 2
        return runIntCodeProgram(codesToRun, inputs).outputs.last().toInt()
    }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 17)[0]
        .split(",")
        .map { it.toLong() }

}

fun main() {
    val day = Day17()
    println(day.runPartOne())
    println(day.runPartTwo())
}
