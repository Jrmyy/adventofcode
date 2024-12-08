package main

import (
	"embed"
	"fmt"
	"reflect"
	"regexp"
	"strconv"
	"strings"

	"adventofcode-go/pkg/aocutils"
)

var bools = map[bool]int{
	true:  1,
	false: 0,
}

//go:embed input.txt
var inputFile embed.FS

func addr(op, registers []int) {
	registers[op[3]] = registers[op[1]] + registers[op[2]]
}

func addi(op, registers []int) {
	registers[op[3]] = registers[op[1]] + op[2]
}

func mulr(op, registers []int) {
	registers[op[3]] = registers[op[1]] * registers[op[2]]
}

func muli(op, registers []int) {
	registers[op[3]] = registers[op[1]] * op[2]
}

func banr(op, registers []int) {
	registers[op[3]] = registers[op[1]] & registers[op[2]]
}

func bani(op, registers []int) {
	registers[op[3]] = registers[op[1]] & op[2]
}

func borr(op, registers []int) {
	registers[op[3]] = registers[op[1]] | registers[op[2]]
}

func bori(op, registers []int) {
	registers[op[3]] = registers[op[1]] | op[2]
}

func setr(op, registers []int) {
	registers[op[3]] = registers[op[1]]
}

func seti(op, registers []int) {
	registers[op[3]] = op[1]
}

func gtir(op, registers []int) {
	a := op[1]
	b := registers[op[2]]
	registers[op[3]] = bools[a > b]
}

func gtri(op, registers []int) {
	a := registers[op[1]]
	b := op[2]
	registers[op[3]] = bools[a > b]
}

func gtrr(op, registers []int) {
	a := registers[op[1]]
	b := registers[op[2]]
	registers[op[3]] = bools[a > b]
}

func eqir(op, registers []int) {
	a := op[1]
	b := registers[op[2]]
	registers[op[3]] = bools[a == b]
}

func eqri(op, registers []int) {
	a := registers[op[1]]
	b := op[2]
	registers[op[3]] = bools[a == b]
}

func eqrr(op, registers []int) {
	a := registers[op[1]]
	b := registers[op[2]]
	registers[op[3]] = bools[a == b]
}

var operators = map[string]func([]int, []int){
	"addr": addr,
	"addi": addi,
	"mulr": mulr,
	"muli": muli,
	"banr": banr,
	"bani": bani,
	"borr": borr,
	"bori": bori,
	"setr": setr,
	"seti": seti,
	"gtir": gtir,
	"gtri": gtri,
	"gtrr": gtrr,
	"eqir": eqir,
	"eqri": eqri,
	"eqrr": eqrr,
}
var re = regexp.MustCompile(`(?m)(\d+)`)

func parseRegisters(rawRegisters string) []int {
	var registers []int
	for _, match := range re.FindAllString(rawRegisters, -1) {
		i, _ := strconv.Atoi(match)
		registers = append(registers, i)
	}
	return registers
}

func parseOp(rawOp string) []int {
	parts := strings.Split(rawOp, " ")
	op := make([]int, len(parts))
	for idx, value := range parts {
		i, _ := strconv.Atoi(value)
		op[idx] = i
	}
	return op
}

func runPartOne(lines []string) int {
	idx := 0
	cnt := 0
	for idx < len(lines) {
		rawBefore, rawOp, rawAfter := lines[idx], lines[idx+1], lines[idx+2]
		if rawBefore == "" {
			break
		}
		before := parseRegisters(rawBefore)
		after := parseRegisters(rawAfter)
		op := parseOp(rawOp)

		opMatch := 0
		for _, opFn := range operators {
			opBefore := make([]int, len(before))
			copy(opBefore, before)
			opFn(op, opBefore)
			if reflect.DeepEqual(opBefore, after) {
				opMatch++
			}
		}
		if opMatch >= 3 {
			cnt++
		}

		idx += 4
	}
	return cnt
}

func runPartTwo(lines []string) int {
	idx := 0
	possibleMatches := map[int]map[string]bool{}
	for idx < len(lines) {
		rawBefore, rawOp, rawAfter := lines[idx], lines[idx+1], lines[idx+2]
		if rawBefore == "" {
			break
		}
		before := parseRegisters(rawBefore)
		after := parseRegisters(rawAfter)
		op := parseOp(rawOp)

		for opName, opFn := range operators {
			opBefore := make([]int, len(before))
			copy(opBefore, before)
			opFn(op, opBefore)
			if reflect.DeepEqual(opBefore, after) {
				if matches, ok := possibleMatches[op[0]]; !ok {
					possibleMatches[op[0]] = map[string]bool{
						opName: true,
					}
				} else {
					matches[opName] = true
				}
			}
		}
		idx += 4
	}

	realMatches := map[int]string{}
	for len(realMatches) != len(possibleMatches) {
		var toDelete string
		for opCode, possible := range possibleMatches {
			if len(possible) == 1 {
				for opName := range possible {
					realMatches[opCode] = opName
					toDelete = opName
				}
			}
			break
		}
		for _, possible := range possibleMatches {
			delete(possible, toDelete)
		}
	}

	registers := []int{0, 0, 0, 0}

	for idx < len(lines) {
		line := lines[idx]
		idx++
		if line == "" {
			continue
		}

		op := parseOp(line)
		opFn := operators[realMatches[op[0]]]
		opFn(op, registers)
	}

	return registers[0]
}

func main() {
	lines := aocutils.MustGetDayInput(inputFile)
	fmt.Println(runPartOne(lines))
	fmt.Println(runPartTwo(lines))
}
