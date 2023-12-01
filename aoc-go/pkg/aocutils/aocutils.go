package aocutils

import (
	"bufio"
	"embed"
	"io/fs"
)

func MustGetDayInput(embedInput embed.FS) []string {
	file, err := embedInput.Open("input.txt")
	if err != nil {
		panic(err)
	}

	defer closeFile(file)

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	return lines
}

func GenerateSequence(x, y int) []int {
	l := make([]int, y-x+1)
	for i := x; i <= y; i++ {
		l[i-x] = i
	}
	return l
}

func closeFile(f fs.File) {
	err := f.Close()
	if err != nil {
		panic(err)
	}
}
