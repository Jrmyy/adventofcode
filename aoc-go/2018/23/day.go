package main

import (
	"embed"
	"fmt"
	"regexp"

	"adventofcode-go/pkg/aocutils"
)

//go:embed input.txt
var inputFile embed.FS

var regex = regexp.MustCompile("^pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)$")

func parseInput(lines []string) []aocutils.Octahedron {
	nanobots := make([]aocutils.Octahedron, len(lines))
	for idx, line := range lines {
		match := regex.FindStringSubmatch(line)
		nanobots[idx] = aocutils.Octahedron{
			Center: aocutils.Point{
				X: aocutils.MustStringToInt(match[1]),
				Y: aocutils.MustStringToInt(match[2]),
				Z: aocutils.MustStringToInt(match[3]),
			},
			R: aocutils.MustStringToInt(match[4]),
		}
	}
	return nanobots
}

func runPartOne(lines []string) int {
	nanobots := parseInput(lines)
	mRadius := 0
	maxCnt := 0
	for _, n1 := range nanobots {
		cnt := 0
		for _, n2 := range nanobots {
			if n1.Overlaps(n2) {
				cnt++
			}
		}
		if n1.R >= mRadius {
			maxCnt = cnt
			mRadius = n1.R
		}
	}

	return maxCnt
}

func main() {
	ipt := aocutils.MustGetDayInput(inputFile)
	fmt.Println(runPartOne(ipt))
}
