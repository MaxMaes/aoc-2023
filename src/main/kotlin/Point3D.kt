data class Point3D(var x: Int, var y: Int, var z: Int) {
    fun move(direction: Direction3D): Point3D {
        val point = direction.toPoint3D()
        return this + point
    }

    operator fun plus(other: Point3D): Point3D = Point3D(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun times(other: Int): Point3D = Point3D(this.x * other, this.y * other, this.z * other)
    operator fun times(other: Point3D): Point3D = Point3D(this.x * other.x, this.y * other.y, this.z * other.z)
}