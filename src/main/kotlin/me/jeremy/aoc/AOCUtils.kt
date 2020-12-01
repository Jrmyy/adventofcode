package me.jeremy.aoc

object AOCUtils {

    fun getDayInput(day: Int): List<String> =
        javaClass.classLoader.getResourceAsStream("day${day}.txt")
            .bufferedReader()
            .readLines()

}
