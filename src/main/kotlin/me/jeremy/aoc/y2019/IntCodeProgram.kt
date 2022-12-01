package me.jeremy.aoc.y2019

data class IntCodeProgramResult(
    val codes: MutableList<Long>,
    val currentIdx: Int,
    val currentIptIdx: Int,
    val relativeBase: Int,
    val outputs: List<Long>
)

open class IntCodeProgram {

    fun runIntCodeProgram(
        codes: MutableList<Long>,
        inputs: List<Long> = listOf(),
        hasOptMode: Boolean = true,
        initialCurrentIdx: Int = 0,
        initialCurrentIptIdx: Int = 0,
        initialRelativeBase: Int = 0,
        maxOutputSize: Int? = null,
        defaultIfNotEnoughInput: Long? = null
    ): IntCodeProgramResult {
        var currentIdx = initialCurrentIdx
        var currentIptIdx = initialCurrentIptIdx
        val outputs = mutableListOf<Long>()
        var relativeBase = initialRelativeBase
        while (true) {
            val code = codes[currentIdx].toInt()
            if (code == 99) {
                if (!hasOptMode) {
                    outputs.add(codes[0])
                }
                break
            }
            val parsedCode = codes[currentIdx].toString().padStart(5, '0')
            val opCode = parsedCode.takeLast(2).toInt()
            val firstMode = parsedCode[2]
            val secondMode = parsedCode[1]
            val thirdMode = parsedCode[0]
            val first = getElement(codes, currentIdx + 1, relativeBase, firstMode)

            when (opCode) {
                3 -> {
                    // Meaning we need instructions from next amplifier so we return and wait
                    if (currentIptIdx == inputs.size && defaultIfNotEnoughInput == null) {
                        break
                    }
                    val iptIdxInCode = getOptElement(codes, currentIdx + 1, relativeBase, firstMode)
                    codes[iptIdxInCode] = inputs.getOrNull(currentIptIdx) ?: defaultIfNotEnoughInput!!
                    currentIptIdx += 1
                    currentIdx += 2
                }
                4 -> {
                    outputs.add(first)
                    currentIdx += 2
                    if (maxOutputSize != null && outputs.size == maxOutputSize) {
                        break
                    }
                }
                9 -> {
                    relativeBase += first.toInt()
                    currentIdx += 2
                }
                else -> {
                    val second = getElement(codes, currentIdx + 2, relativeBase, secondMode)
                    if (listOf(1, 2, 7, 8).contains(opCode)) {
                        val third = getOptElement(codes, currentIdx + 3, relativeBase, thirdMode)
                        try {
                            codes[third]
                        } catch (e: IndexOutOfBoundsException) {
                            val diff = third - codes.size
                            codes.addAll((0..diff).map { 0L })
                        }
                        when (opCode) {
                            1 -> {
                                codes[third] = first + second
                            }
                            2 -> {
                                codes[third] = first * second
                            }
                            7 -> {
                                codes[third] = if (first < second) 1 else 0
                            }
                            8 -> {
                                codes[third] = if (first == second) 1 else 0
                            }
                        }
                        currentIdx += 4
                    } else {
                        when (opCode) {
                            5 -> {
                                if (first != 0L) {
                                    currentIdx = second.toInt()
                                } else {
                                    currentIdx += 3
                                }
                            }
                            6 -> {
                                if (first == 0L) {
                                    currentIdx = second.toInt()
                                } else {
                                    currentIdx += 3
                                }
                            }
                        }
                    }
                }
            }
        }
        return IntCodeProgramResult(
            codes.toMutableList(),
            currentIdx,
            currentIptIdx,
            relativeBase,
            outputs
        )
    }

    private fun getOptElement(codes: MutableList<Long>, currentIdx: Int, relativeBase: Int, mode: Char): Int =
        if (mode == '0') {
            codes[currentIdx]
        } else {
            relativeBase + codes[currentIdx]
        }.toInt()

    private fun getElement(codes: MutableList<Long>, currentIdx: Int, relativeBase: Int, mode: Char): Long =
        try {
            codes[
                when (mode) {
                    '0' -> {
                        codes[currentIdx]
                    }
                    '1' -> {
                        currentIdx
                    }
                    else -> {
                        relativeBase + codes[currentIdx]
                    }
                }.toInt()
            ]
        } catch (e: IndexOutOfBoundsException) {
            0L
        }
}
