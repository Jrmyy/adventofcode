package main

import (
	"embed"
	"fmt"
	"strings"

	"adventofcode-go/pkg/aocutils"
	shared2018 "adventofcode-go/pkg/shared/2018"
)

//go:embed input.txt
var inputFile embed.FS

func runBackgroundProcess(ipt []string, registers []int) int {
	instructionPointer := aocutils.MustStringToInt(strings.TrimPrefix(ipt[0], "#ip "))
	instructions := ipt[1:]
	for registers[instructionPointer] < len(instructions) {
		pos := registers[instructionPointer]
		instruction := instructions[pos]
		parts := strings.Split(instruction, " ")
		opName := parts[0]
		opArgs := make([]int, len(parts)-1)
		for idx, i := range parts[1:] {
			opArgs[idx] = aocutils.MustStringToInt(i)
		}
		opFn := shared2018.Operators[opName]
		opFn(opArgs, registers)
		registers[instructionPointer]++
	}
	return registers[0]
}

func runPartOne(ipt []string) int {
	return runBackgroundProcess(ipt, []int{0, 0, 0, 0, 0, 0})
}

func runPartTwo(_ []string) int {
	// After checking the outputs
	// [1 10551260 10551261 1 12 1]
	// [3 10551260 10551261 2 12 1]
	// [3 10551260 10551261 3 12 1]
	// [7 10551260 10551261 4 12 1]
	// [12 10551260 10551261 5 12 1]
	// [12 10551260 10551261 6 12 1]
	// [12 10551260 10551261 7 12 1]
	// [12 10551260 10551261 8 12 1]
	// [12 10551260 10551261 9 12 1]
	// [22 10551260 10551261 10 12 1]
	// So it seems that in register 0, we add from 1 to 10551260 all the dividers of 10551260
	cnt := 0
	for i := 1; i <= 10551260; i++ {
		if 10551260%i == 0 {
			cnt += i
		}
	}
	return cnt
}

func main() {
	lines := aocutils.MustGetDayInput(inputFile)
	fmt.Println(runPartOne(lines))
	fmt.Println(runPartTwo(lines))
}
