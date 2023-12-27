"""
For part 1, see day.go
Part 2:
We take the first 3 hailstone, and we have this system of equations
xa + ta * vxa = xr + ta * vxr
ya + ta * vya = yr + ta * vyr
za + ta * vza = zr + ta * vzr
xb + tb * vxb = xr + tb * vxr
yb + tb * vyb = yr + tb * vyr
zb + tb * vzb = zr + tb * vzr
xc + tc * vxc = xr + tc * vxr
yc + tc * vyc = yr + tc * vyr
zc + tc * vzc = zr + tc * vzr
Once we have this system of 9 equations, we just need to solve it and return the sum of xr, yr and zr.
"""

from sympy import *

with open("input.txt") as f_in:
    lines = f_in.readlines()
    for idx, line in enumerate(lines):
        pos, speed = line.split(" @ ")
        new_line = [
            int(p.strip()) for p in pos.split(", ") + speed.split(", ")
        ]
        lines[idx] = new_line

xa = lines[0][0]
ya = lines[0][1]
za = lines[0][2]
vxa = lines[0][3]
vya = lines[0][4]
vza = lines[0][5]
xb = lines[1][0]
yb = lines[1][1]
zb = lines[1][2]
vxb =lines[1][3]
vyb =lines[1][4]
vzb =lines[1][5]
xc = lines[2][0]
yc = lines[2][1]
zc = lines[2][2]
vxc = lines[2][3]
vyc = lines[2][4]
vzc = lines[2][5]

ta, tb, tc, xr, yr, zr, vxr, vyr, vzr = symbols('ta, tb, tc, xr, yr, zr, vxr, vyr, vzr')

z = solve([
    xr - xa + ta * (vxr - vxa),
    yr - ya + ta * (vyr - vya),
    zr - za + ta * (vzr - vza),
    xr - xb + tb * (vxr - vxb),
    yr - yb + tb * (vyr - vyb),
    zr - zb + tb * (vzr - vzb),
    xr - xc + tc * (vxr - vxc),
    yr - yc + tc * (vyr - vyc),
    zr - zc + tc * (vzr - vzc),
], (xr, yr, zr, ta, tb, tc, vxr, vyr, vzr))

print(z[0][0] + z[0][1] + z[0][2])
