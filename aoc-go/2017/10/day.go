package main

import (
	"embed"
	"fmt"
	"strings"

	"adventofcode-go/pkg/aocutils"
	"adventofcode-go/pkg/cast"
)

//go:embed input.txt
var inputFile embed.FS

func generateSequence(x, y int) []int {
	l := make([]int, y-x+1)
	for i := x; i <= y; i++ {
		l[i-x] = i
	}
	return l
}

func runKnotHash(l []int, instructions []int, numRounds int) {
	skip, idx := 0, 0
	for r := 0; r < numRounds; r++ {
		for _, ins := range instructions {
			for i := 0; i < ins/2; i++ {
				from := (idx + i) % len(l)
				to := (idx + ins - 1 - i) % len(l)
				l[from], l[to] = l[to], l[from]
			}
			idx += ins + skip
			skip++
		}
	}
}

func runPartOne(instructions []int) int {
	l := generateSequence(0, 255)
	runKnotHash(l, instructions, 1)
	return l[0] * l[1]
}

func runPartTwo(instructions []int) string {
	l := generateSequence(0, 255)
	runKnotHash(l, instructions, 64)
	blocks := make([]string, 16)
	for b := 0; b < 16; b++ {
		lb := l[b*16+1 : (b+1)*16]
		xorOpt := l[b*16]
		for _, el := range lb {
			xorOpt = xorOpt ^ el
		}
		blocks[b] = fmt.Sprintf("%x", xorOpt)
	}
	return strings.Join(blocks, "")
}

func parseInputP1(line string) []int {
	figures := strings.Split(line, ",")
	ipt := make([]int, len(figures))
	for idx, fig := range figures {
		ipt[idx] = cast.MustStringToInt(fig)
	}
	return ipt
}

func parseInputP2(line string) []int {
	var instructions []int
	for _, unicode := range line {
		instructions = append(instructions, int(unicode))
	}
	instructions = append(instructions, []int{17, 31, 73, 47, 23}...)
	return instructions
}

func main() {
	line := aocutils.MustGetDayInput(inputFile)[0]
	instructionsP1 := parseInputP1(line)
	fmt.Println(runPartOne(instructionsP1))
	instructionsP2 := parseInputP2(line)
	fmt.Println(runPartTwo(instructionsP2))
}
