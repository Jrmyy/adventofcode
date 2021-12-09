package me.jeremy.aoc.y2019

import me.jeremy.aoc.AOCUtils
import me.jeremy.aoc.Day
import java.util.Collections.swap

class Day7 : IntCodeProgram(), Day<List<Long>, Long> {
    override fun runPartOne(): Long {
        val codes = getInput().toMutableList()
        return permutations((0..4).toList().map { it.toLong() }).map {
            val first = runIntCodeProgram(codes, listOf(it[0], 0)).outputs[0]
            val second = runIntCodeProgram(codes, listOf(it[1], first)).outputs[0]
            val third = runIntCodeProgram(codes, listOf(it[2], second)).outputs[0]
            val fourth = runIntCodeProgram(codes, listOf(it[3], third)).outputs[0]
            Pair(it, runIntCodeProgram(codes, listOf(it[4], fourth)).outputs[0])
        }
            .maxByOrNull { it.second }
            ?.second
            ?: throw RuntimeException("oups")
    }

    override fun runPartTwo(): Long {
        val codes = getInput().toMutableList()
        return permutations((5..9).toList().map { it.toLong() }).map {
            var outputs = listOf(-1L)
            val inputs = it.map { that ->
                mutableListOf(that)
            }
            val softwares = it.map {
                IntCodeProgramResult(
                    codes.toMutableList(),
                    0,
                    0,
                    0,
                    mutableListOf()
                )
            }.toMutableList()
            inputs[0].add(0)
            while (outputs.isNotEmpty()) {
                for (i in 0 until 5) {
                    val res = runIntCodeProgram(
                        softwares[i].codes,
                        inputs[i],
                        initialCurrentIdx = softwares[i].currentIdx,
                        initialCurrentIptIdx = softwares[i].currentIptIdx
                    )
                    outputs = res.outputs
                    softwares[i] = res
                    inputs[(i + 1) % 5].addAll(outputs)
                    if (outputs.isEmpty()) {
                        break
                    }
                }
            }
            inputs[0].last()
        }.maxOrNull() ?: throw RuntimeException(":(")
    }

    private fun permutations(list: List<Long>): List<List<Long>> {
        val retVal: MutableList<List<Long>> = mutableListOf()
        fun generate(k: Int, list: List<Long>) {
            // If only 1 element, just output the array
            if (k == 1) {
                retVal.add(list.toList())
            } else {
                for (i in 0 until k) {
                    generate(k - 1, list)
                    if (k % 2 == 0) {
                        swap(list, i, k - 1)
                    } else {
                        swap(list, 0, k - 1)
                    }
                }
            }
        }
        generate(list.size, list)
        return retVal
    }

    override fun getInput(): List<Long> = AOCUtils.getDayInput(2019, 7)[0].split(",").map { it.toLong() }
}

fun main() {
    val day = Day7()
    println(day.runPartOne())
    println(day.runPartTwo())
}
