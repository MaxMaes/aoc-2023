class Rect3D(startField: Point3D, endField: Point3D) {
    var start: Point3D = startField
        private set;
    var end: Point3D = endField
        private set;

    val bottom get() = minOf(start.z, end.z)
    val top get() = maxOf(start.z, end.z)
    val left get() = minOf(start.x, end.x)
    val right get() = maxOf(start.x, end.x)
    val front get() = minOf(start.y, end.y)
    val back get() = maxOf(start.y, end.y)

    override fun toString(): String {
        return "Rect3D(start=$start, end=$end)"
    }

    fun move(direction: Direction3D) {
        val point = direction.toPoint3D()
        start += point
        end += point
    }

    fun intersects(other: Rect3D): Boolean {
        return !(other.left > this.right
                || other.right < this.left
                || other.front > this.back
                || other.back < this.front
                || other.bottom > this.top
                || other.top < this.bottom)
    }

    /**
     * Returns a list of all Rect3D shapes that are directly supported by shape
     */
    fun supports(stack: List<Rect3D>): List<Rect3D> {
        val point = Direction3D.UP.toPoint3D()
        val movedRect = Rect3D(start + point, end + point)
        return stack.filter { it.intersects(movedRect) }
    }

    fun touchesInStack(direction: Direction3D, stack: List<Rect3D>): Boolean {
        val point = direction.toPoint3D()
        val movedRect = Rect3D(start + point, end + point)
        return stack.any { it.intersects(movedRect) }
    }
}