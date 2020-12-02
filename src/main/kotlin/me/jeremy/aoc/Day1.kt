package me.jeremy.aoc

class Day1: Day<Int, Int> {

    override fun runPartOne(): Int {
        val numbers = getInput()
        val pair = numbers.flatMapIndexed { idxIt, it ->
            numbers.mapIndexed { idxThat, that -> if (idxIt < idxThat) {
                Pair(it, that)
            } else {
                null
            }}
        }
            .filterNotNull()
            .firstOrNull { it.first + it.second == 2020 }
            ?: throw RuntimeException("Pair must not be null")
        return pair.first * pair.second
    }

    override fun runPartTwo(): Int {
        val numbers = getInput()
        val triple = numbers
            .flatMapIndexed { idxIt, it ->
                numbers.flatMapIndexed { idxThat, that ->
                    numbers.mapIndexed { idxThose, those ->
                        if (idxThat in (idxIt + 1) until idxThose) {
                            Triple(it, that, those)
                        } else {
                            null
                        }
                    }
                }
            }
            .filterNotNull()
            .firstOrNull {
                it.first + it.second + it.third == 2020
            }
            ?: throw RuntimeException("Triple must not be null")
        return triple.first * triple.second * triple.third
    }

    override fun getInput(): List<Int> = AOCUtils.getDayInput(1).map { it.toInt() }

}

fun main() {
    val day = Day1()
    println(day.runPartOne())
    println(day.runPartTwo())
}
