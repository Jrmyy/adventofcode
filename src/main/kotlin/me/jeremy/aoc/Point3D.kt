package me.jeremy.aoc

data class Point3D(
    val x: Double,
    val y: Double,
    val z: Double
) {
    fun add(point: Point3D): Point3D = Point3D(x + point.x, y + point.y, z + point.z)

    fun subtract(point: Point3D): Point3D = Point3D(x - point.x,y - point.y, z - point.z)
}
