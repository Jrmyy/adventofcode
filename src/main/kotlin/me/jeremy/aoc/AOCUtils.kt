package me.jeremy.aoc

object AOCUtils {

    fun getDayInput(year: Int, day: Int): List<String> =
        javaClass.classLoader.getResourceAsStream("${year}/day${day}.txt")
            .bufferedReader()
            .readLines()

    fun getAdjacentPositions(l: List<List<Any>>, y: Int, x: Int, diagCount: Boolean = false): List<Pair<Int, Int>> =
        listOf(
            Pair(x + 1, y),
            Pair(x - 1, y),
            Pair(x, y - 1),
            Pair(x, y + 1),
        ).let {
            if (diagCount) it + listOf(
                Pair(x - 1, y - 1),
                Pair(x + 1, y - 1),
                Pair(x - 1, y + 1),
                Pair(x + 1, y + 1)
            ) else it
        }.filter {
            it.second in l.indices && it.first in l.first().indices
        }

}
