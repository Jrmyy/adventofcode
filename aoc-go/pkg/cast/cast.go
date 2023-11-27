package cast

import (
	"log"
	"strconv"
)

func MustStringToInt(s string) int {
	i, err := strconv.Atoi(s)
	if err != nil {
		log.Fatal(err)
	}
	return i
}
