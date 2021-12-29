package me.jeremy.aoc.y2015

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day19 : Day<Pair<List<Pair<String, String>>, String>, Int> {
    override fun runPartOne(): Int {
        val (reactions, molecule) = getInput()
        val possibilities = mutableSetOf<String>()
        reactions.forEach {
            computeAllAlternatives(molecule, it, possibilities)
        }
        return possibilities.count()
    }

    override fun runPartTwo(): Int {
        TODO("Not yet implemented")
    }

    override fun getInput(): Pair<List<Pair<String, String>>, String> {
        val lines = AOCUtils.getDayInput(2015, 19)
        return Pair(
            lines.subList(0, lines.size - 2).map {
                it.split(" => ").let { p -> Pair(p.first(), p.last()) }
            },
            lines.last()
        )
    }

    private fun computeAllAlternatives(
        molecule: String, reaction: Pair<String, String>, possibilities: MutableSet<String>
    ) {
        val (left, right) = reaction
        var startIndex = 0
        var idx = molecule.indexOf(left, startIndex)
        while (idx != -1) {
            val lm = if (idx > 0) molecule.substring(0, idx) else ""
            possibilities.add(
                "$lm$right${molecule.substring(idx + left.length)}"
            )
            startIndex = idx + 1
            idx = molecule.indexOf(left, startIndex)
        }
    }
}

fun main() {
    val day = Day19()
    println(day.runPartOne())
    println(day.runPartTwo())
}
