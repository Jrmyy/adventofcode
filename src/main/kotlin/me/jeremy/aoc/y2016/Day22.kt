package me.jeremy.aoc.y2016

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day22 : Day<List<Triple<String, Int, Int>>, Int> {

    override fun runPartOne(): Int {
        val nodes = getInput()
        return nodes.mapIndexed { index, node ->
            if (node.second == 0) {
                0
            } else {
                val cpNodes = nodes.toMutableList()
                cpNodes.removeAt(index)
                cpNodes.count { node.second < it.third }
            }
        }.sum()
    }

    override fun runPartTwo(): Int {
        TODO("Not yet implemented")
    }

    override fun getInput(): List<Triple<String, Int, Int>> = AOCUtils.getDayInput(2016, 22).drop(2).map {
        val match = "(.*) +\\d+T +(\\d+)T +(\\d+)T +\\d+%".toRegex().find(it)!!.groupValues
        Triple(match[1].trim(), match[2].toInt(), match[3].toInt())
    }
}

fun main() {
    val day = Day22()
    println(day.runPartOne())
}
