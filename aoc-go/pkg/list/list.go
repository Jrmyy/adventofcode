package list

import (
	"slices"

	"golang.org/x/exp/constraints"
)

type Number interface {
	constraints.Integer | constraints.Float
}

type op int

const (
	opMin op = iota
	opMax
)

func Sorted[T Number](l []T) []T {
	return SortedBy(
		l,
		func(i, j T) int {
			if i == j {
				return 0
			}
			if i < j {
				return -1
			}
			return 1
		},
	)
}

func SortedBy[T Number](l []T, comparator func(i, j T) int) []T {
	sorted := make([]T, len(l))
	copy(sorted, l)
	slices.SortStableFunc(sorted, comparator)
	return sorted
}

func SortedDescending[T Number](l []T) []T {
	return SortedBy(
		l,
		func(i, j T) int {
			if i == j {
				return 0
			}
			if i < j {
				return 1
			}
			return -1
		},
	)
}

func SumOf[T Number](l []T) T {
	return SumBy(
		l,
		func(x T) T { return x },
	)
}

func SumBy[T Number, TT Number](l []T, callable func(x T) TT) TT {
	var acc TT = 0
	for _, el := range l {
		acc += callable(el)
	}
	return acc
}

func MaxOf[T Number](l []T) T {
	return MaxBy(
		l,
		func(x T) T { return x },
	)
}

func MaxBy[T Number, TT Number](l []T, callable func(x T) TT) TT {
	return maxOrMinBy(opMax, l, callable)
}

func MinOf[T Number](l []T) T {
	return MinBy(
		l,
		func(x T) T { return x },
	)
}

func MinBy[T Number, TT Number](l []T, callable func(x T) TT) TT {
	return maxOrMinBy(opMin, l, callable)
}

func maxOrMinBy[T Number, TT Number](rule op, l []T, callable func(x T) TT) TT {
	var m = callable(l[0])
	for _, el := range l[1:] {
		i := callable(el)
		var condition bool
		if rule == opMin {
			condition = i < m
		} else if rule == opMax {
			condition = i > m
		}
		if condition {
			m = i
		}
	}
	return m
}
