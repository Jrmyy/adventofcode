package me.jeremy.aoc

object AOCUtils {

    fun getDayInput(year: Int, day: Int): List<String> =
        javaClass.classLoader.getResourceAsStream("${year}/day${day}.txt")
            .bufferedReader()
            .readLines()

}
