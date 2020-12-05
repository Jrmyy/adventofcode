package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day

class Day13: IntCodeProgram(), Day<List<Long>, Int> {
    override fun runPartOne(): Int =
        runIntCodeProgram(
            getInput().toMutableList(),
            listOf()
        )
            .outputs
            .chunked(3)
            .map {
                it.map { that -> that.toInt() }
            }
            .filter { it[2] == 2 }.size

    override fun runPartTwo(): Int {
        var codes = getInput().toMutableList()
        var position = 0
        var paddleX = 0
        var currentIdx = 0
        var currentInputIdx = 0
        var currentRelativeBase = 0
        var currentScore = 0
        codes[0] = 2
        while (true) {
            val res = runIntCodeProgram(
                codes,
                listOf(position.toLong()),
                initialCurrentIdx = currentIdx,
                initialCurrentIptIdx = currentInputIdx,
                initialRelativeBase = currentRelativeBase,
                maxOutputSize = 3,
                defaultIfNotEnoughInput = position.toLong()
            )
            codes = res.codes
            currentIdx = res.currentIdx
            currentInputIdx = res.currentIptIdx
            currentRelativeBase = res.relativeBase
            val outputs = res.outputs
            if (outputs.size != 3) {
                break
            }
            if (outputs[0] == -1L && outputs[1] == 0L) {
                currentScore = outputs[2].toInt()
            } else {
                val (x, _, tileId) = outputs
                val newPosAndPaddle = adaptPosition(x.toInt(), tileId.toInt(), position, paddleX)
                position = newPosAndPaddle.first
                paddleX = newPosAndPaddle.second
            }
        }
        return currentScore
    }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 13)[0].split(",").map { it.toLong() }

    private fun adaptPosition(x: Int, tileId: Int, position: Int, paddleX: Int): Pair<Int, Int> {
        return when (tileId) {
            4 -> {
                val newPos = when {
                    x > paddleX -> 1
                    x < paddleX -> -1
                    else -> 0
                }
                Pair(newPos, paddleX)
            }
            3 -> {
                Pair(position, x)
            }
            else -> Pair(position, paddleX)
        }
    }

}

fun main() {
    val day = Day13()
    println(day.runPartOne())
    println(day.runPartTwo())
}
