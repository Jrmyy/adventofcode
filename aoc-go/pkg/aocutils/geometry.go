package aocutils

import (
	"math"
	"os"
)

type Point struct {
	X int
	Y int
}

func (p Point) Neighbours(includeDiag bool) []Point {
	neighbours := []Point{
		{X: p.X - 1, Y: p.Y},
		{X: p.X + 1, Y: p.Y},
		{X: p.X, Y: p.Y - 1},
		{X: p.X, Y: p.Y + 1},
	}
	if includeDiag {
		neighbours = append(neighbours, []Point{
			{X: p.X - 1, Y: p.Y - 1},
			{X: p.X - 1, Y: p.Y + 1},
			{X: p.X + 1, Y: p.Y - 1},
			{X: p.X + 1, Y: p.Y + 1},
		}...)
	}
	return neighbours
}

func (p Point) Add(o Point) Point {
	return Point{X: p.X + o.X, Y: p.Y + o.Y}
}

func (p Point) Sub(o Point) Point {
	return Point{X: p.X - o.X, Y: p.Y - o.Y}
}

type Edges[T comparable] map[T]int

// Graph structure represents a weighted graph, with a mapping between the vertices and their edges to the
// other vertices. The only condition is that T must be comparable to be a map key.
type Graph[T comparable] map[T]Edges[T]

func (g Graph[T]) Dijkstra(start T) (map[T]int, map[T][]T) {
	dist := map[T]int{}
	seen := map[T]bool{}
	prev := map[T][]T{}
	for s, ds := range g {
		dist[s] = math.MaxInt
		for d := range ds {
			dist[d] = math.MaxInt
		}
	}
	dist[start] = 0
	for len(seen) < len(dist) {
		var closestNotSeen T
		m := math.MaxInt
		for p, dp := range dist {
			if _, ok := seen[p]; !ok && dp < m {
				m = dp
				closestNotSeen = p
			}
		}
		if _, ok := g[closestNotSeen]; m == math.MaxInt || !ok {
			os.Exit(1)
		}

		for neighbor, weight := range g[closestNotSeen] {
			alt := dist[closestNotSeen] + weight
			if alt < dist[neighbor] {
				dist[neighbor] = alt
				prev[neighbor] = []T{closestNotSeen}
			} else if alt == dist[neighbor] {
				prev[neighbor] = append(prev[neighbor], closestNotSeen)
			}
		}
		seen[closestNotSeen] = true
	}
	return dist, prev
}
