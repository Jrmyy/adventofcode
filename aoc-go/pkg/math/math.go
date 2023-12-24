package math

func GcdList(l []int) int {
	g := l[1]
	for _, i := range l[1:] {
		g = Gcd(g, i)
	}
	return g
}

func Gcd(a, b int) int {
	for a != b {
		if a > b {
			a -= b
		} else {
			b -= a
		}
	}
	return a
}

func Lcm(a, b int) int {
	return a * b / Gcd(a, b)
}

func LcmList(l []int) int {
	lcm := l[0]
	for _, i := range l[1:] {
		lcm = Lcm(lcm, i)
	}
	return lcm
}
