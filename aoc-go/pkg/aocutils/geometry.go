package aocutils

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
